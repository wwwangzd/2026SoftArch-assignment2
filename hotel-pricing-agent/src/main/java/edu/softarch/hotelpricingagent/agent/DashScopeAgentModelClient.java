package edu.softarch.hotelpricingagent.agent;

import java.time.Duration;
import java.time.Instant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "agent.model-mode", havingValue = "dashscope")
public class DashScopeAgentModelClient implements AgentModelClient {

	private static final String CONVERSATION_ID = "hotel-pricing-agent-session";

	private final ChatClient chatClient;

	private final MessageChatMemoryAdvisor chatMemoryAdvisor;

	public DashScopeAgentModelClient(ChatModel chatModel) {
		this.chatClient = ChatClient.create(chatModel);
		var chatMemory = MessageWindowChatMemory.builder()
				.chatMemoryRepository(new InMemoryChatMemoryRepository())
				.build();
		this.chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
				.conversationId(CONVERSATION_ID)
				.build();
	}

	@Override
	public AgentModelResponse generate(AgentModelRequest request) {
		Instant startedAt = Instant.now();
		ChatResponse response = chatClient.prompt()
				.system(request.systemPrompt())
				.user(request.userPrompt())
				.advisors(chatMemoryAdvisor)
				.call()
				.chatResponse();
		String content = response.getResult().getOutput().getText();
		long elapsedMillis = Duration.between(startedAt, Instant.now()).toMillis();
		Usage usage = response.getMetadata().getUsage();
		Integer promptTokens = usage == null ? null : usage.getPromptTokens();
		Integer completionTokens = usage == null ? null : usage.getCompletionTokens();
		return new AgentModelResponse(content, elapsedMillis, promptTokens, completionTokens);
	}
}
