package edu.softarch.hotelpricingagent.agent;

import java.time.Duration;
import java.time.Instant;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "agent.model-mode", havingValue = "dry-run", matchIfMissing = true)
public class DryRunAgentModelClient implements AgentModelClient {

	@Override
	public AgentModelResponse generate(AgentModelRequest request) {
		Instant startedAt = Instant.now();
		String content = """
				[DRY-RUN RESPONSE]

				Model call was skipped intentionally. This placeholder confirms that the agent
				can build prompts, preserve the required Qwen3-Max model setting, execute the
				ADD iteration workflow, and write the conversation log without using external
				domain knowledge.

				Call: %s
				Model: %s
				""".formatted(request.callName(), request.modelName());
		long elapsedMillis = Duration.between(startedAt, Instant.now()).toMillis();
		return new AgentModelResponse(content, elapsedMillis, null, null);
	}
}
