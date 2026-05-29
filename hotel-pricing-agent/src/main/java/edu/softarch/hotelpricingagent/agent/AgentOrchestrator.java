package edu.softarch.hotelpricingagent.agent;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.softarch.hotelpricingagent.config.AgentProperties;
import edu.softarch.hotelpricingagent.knowledge.AgentKnowledgeLoader;
import edu.softarch.hotelpricingagent.logging.ConversationLog;
import edu.softarch.hotelpricingagent.logging.ConversationLogger;
import edu.softarch.hotelpricingagent.prompt.PromptBuilder;

@Service
public class AgentOrchestrator {

	private final AgentProperties properties;

	private final AgentKnowledgeLoader knowledgeLoader;

	private final PromptBuilder promptBuilder;

	private final AgentModelClient modelClient;

	private final ConversationLogger conversationLogger;

	public AgentOrchestrator(
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

	public Path run() {
		String priorKnowledge = knowledgeLoader.loadPriorKnowledge();
		String systemPrompt = promptBuilder.buildSystemPrompt(priorKnowledge);
		ConversationLog log = conversationLogger.start(properties.getModelName(), properties.getModelMode());
		List<String> previousSummaries = new ArrayList<>();

		AddIteration[] iterations = AddIteration.values();
		int iterationLimit = Math.min(properties.getMaxIterations(), iterations.length);

		for (int index = 0; index < iterationLimit; index++) {
			AddIteration iteration = iterations[index];
			String iterationOutput = runCall(log, iteration.label(), systemPrompt,
					promptBuilder.buildIterationPrompt(iteration, previousSummaries));

			previousSummaries.add("""
					## %s

					%s
					""".formatted(iteration.label(), iterationOutput));
		}

		conversationLogger.finish(log);
		return log.path();
	}

	private String runCall(ConversationLog log, String callName, String systemPrompt, String userPrompt) {
		AgentModelRequest request = new AgentModelRequest(properties.getModelName(), callName, systemPrompt, userPrompt);
		AgentModelResponse response = modelClient.generate(request);
		conversationLogger.appendInteraction(log, request, response);
		return response.content();
	}
}
