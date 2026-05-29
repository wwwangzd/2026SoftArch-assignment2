package edu.softarch.hotelpricingagent.prompt;

public record PromptTemplates(
		String systemPrompt,
		String iterationPrompt,
		String selfVerificationPrompt) {
}
