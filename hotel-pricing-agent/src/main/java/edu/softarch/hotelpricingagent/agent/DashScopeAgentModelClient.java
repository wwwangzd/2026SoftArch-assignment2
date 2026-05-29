package edu.softarch.hotelpricingagent.agent;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "agent.model-mode", havingValue = "dashscope")
public class DashScopeAgentModelClient implements AgentModelClient {

	private final ChatModel chatModel;

	public DashScopeAgentModelClient(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	@Override
	public AgentModelResponse generate(AgentModelRequest request) {
		Instant startedAt = Instant.now();
		Prompt prompt = new Prompt(List.of(
				new SystemMessage(request.systemPrompt()),
				new UserMessage(request.userPrompt())));
		ChatResponse response = chatModel.call(prompt);
		String content = response.getResult().getOutput().getText();
		long elapsedMillis = Duration.between(startedAt, Instant.now()).toMillis();
		Usage usage = response.getMetadata().getUsage();
		Integer promptTokens = usage == null ? null : usage.getPromptTokens();
		Integer completionTokens = usage == null ? null : usage.getCompletionTokens();
		return new AgentModelResponse(content, elapsedMillis, promptTokens, completionTokens);
	}
}
