package edu.softarch.hotelpricingagent.agent;

public record AgentModelResponse(
		String content,
		long elapsedMillis,
		Integer promptTokens,
		Integer completionTokens) {
}
