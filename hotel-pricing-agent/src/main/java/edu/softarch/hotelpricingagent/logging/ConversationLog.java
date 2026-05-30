package edu.softarch.hotelpricingagent.logging;

import java.nio.file.Path;
import java.time.Instant;

import edu.softarch.hotelpricingagent.agent.AgentModelResponse;

public class ConversationLog {

	private final Path path;

	private final Instant startedAt;

	private int interactionCount;

	private Integer totalTokens;

	public ConversationLog(Path path, Instant startedAt) {
		this.path = path;
		this.startedAt = startedAt;
	}

	public Path path() {
		return path;
	}

	public Instant startedAt() {
		return startedAt;
	}

	public void recordResponse(AgentModelResponse response) {
		interactionCount++;
		if (response.promptTokens() == null || response.completionTokens() == null) {
			return;
		}
		int responseTotalTokens = response.promptTokens() + response.completionTokens();
		totalTokens = totalTokens == null ? responseTotalTokens : totalTokens + responseTotalTokens;
	}

	public int interactionCount() {
		return interactionCount;
	}

	public Integer totalTokens() {
		return totalTokens;
	}
}
