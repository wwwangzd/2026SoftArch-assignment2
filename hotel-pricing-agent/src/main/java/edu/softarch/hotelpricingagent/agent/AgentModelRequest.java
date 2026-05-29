package edu.softarch.hotelpricingagent.agent;

public record AgentModelRequest(
		String modelName,
		String callName,
		String systemPrompt,
		String userPrompt) {
}
