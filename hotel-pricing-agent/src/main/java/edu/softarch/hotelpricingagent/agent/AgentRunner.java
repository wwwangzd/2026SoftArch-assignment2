package edu.softarch.hotelpricingagent.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import edu.softarch.hotelpricingagent.config.AgentProperties;
import edu.softarch.hotelpricingagent.knowledge.AgentKnowledgeLoader;
import edu.softarch.hotelpricingagent.logging.ConversationLog;
import edu.softarch.hotelpricingagent.logging.ConversationLogger;
import edu.softarch.hotelpricingagent.prompt.PromptBuilder;

@Component
@ConditionalOnProperty(name = "agent.run-on-startup", havingValue = "true", matchIfMissing = true)
public class AgentRunner implements CommandLineRunner {

	private static final String[] SESSION_STEPS = {
			"Initial ADD Input Review",
			"Iteration 1: Establishing an Overall System Structure",
			"Iteration 2: Identifying Structures to Support Primary Functionality",
			"Iteration 3: Addressing Reliability and Availability Quality Attributes",
			"Iteration 4: Addressing Development and Operations"
	};

	private final AgentProperties properties;

	private final AgentKnowledgeLoader knowledgeLoader;

	private final PromptBuilder promptBuilder;

	private final AgentModelClient modelClient;

	private final ConversationLogger conversationLogger;

	public AgentRunner(
			AgentProperties properties,
			AgentKnowledgeLoader knowledgeLoader,
			PromptBuilder promptBuilder,
			AgentModelClient modelClient,
			ConversationLogger conversationLogger) {
		this.properties = properties;
		this.knowledgeLoader = knowledgeLoader;
		this.promptBuilder = promptBuilder;
		this.modelClient = modelClient;
		this.conversationLogger = conversationLogger;
	}

	@Override
	public void run(String... args) throws IOException {
		String systemPrompt = promptBuilder.buildSystemPrompt(knowledgeLoader.loadPriorKnowledge());
		ConversationLog log = conversationLogger.start(systemPrompt);
		System.out.println("Interactive ADD architecture agent started.");
		System.out.println("Write your message, then submit a line containing only /send.");
		System.out.println("Use /exit to finish the session.");
		System.out.println("Conversation log: " + normalize(log.path()));

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
			int turn = 1;
			while (true) {
				System.out.println();
				String callName = callName(turn);
				System.out.println(callName + " (/send to submit, /exit to quit):");
				String userPrompt = readUserPrompt(reader);
				if (userPrompt == null) {
					break;
				}
				if (userPrompt.isBlank()) {
					continue;
				}
				AgentModelRequest request = new AgentModelRequest(
						properties.getModelName(),
						callName,
						systemPrompt,
						userPrompt);
				AgentModelResponse response = modelClient.generate(request);
				conversationLogger.appendInteraction(log, request, response);
				System.out.println();
				System.out.println("Agent response:");
				System.out.println(response.content());
				turn++;
			}
		}
		finally {
			conversationLogger.finish(log);
			System.out.println("Interactive ADD architecture agent finished.");
			System.out.println("Conversation log: " + normalize(log.path()));
		}
	}

	private String readUserPrompt(BufferedReader reader) throws IOException {
		StringBuilder builder = new StringBuilder();
		while (true) {
			String line = reader.readLine();
			if (line == null || "/exit".equalsIgnoreCase(line.trim())) {
				return null;
			}
			if ("/send".equalsIgnoreCase(line.trim())) {
				return builder.toString().strip();
			}
			builder.append(line).append(System.lineSeparator());
		}
	}

	private Path normalize(Path path) {
		return path.toAbsolutePath().normalize();
	}

	private String callName(int turn) {
		if (turn <= SESSION_STEPS.length) {
			return SESSION_STEPS[turn - 1];
		}
		return "Follow-up Interaction " + (turn - SESSION_STEPS.length);
	}
}
