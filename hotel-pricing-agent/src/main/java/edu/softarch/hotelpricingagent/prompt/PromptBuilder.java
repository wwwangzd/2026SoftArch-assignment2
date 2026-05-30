package edu.softarch.hotelpricingagent.prompt;

import org.springframework.stereotype.Service;

@Service
public class PromptBuilder {

	private final PromptTemplateLoader templateLoader;

	public PromptBuilder(PromptTemplateLoader templateLoader) {
		this.templateLoader = templateLoader;
	}

	public String buildSystemPrompt(String priorKnowledge) {
		return render(templateLoader.loadSystemPromptTemplate(), "priorKnowledge", priorKnowledge);
	}

	private String render(String template, String... values) {
		if (values.length % 2 != 0) {
			throw new IllegalArgumentException("Template values must be provided as key-value pairs.");
		}
		String rendered = template;
		for (int index = 0; index < values.length; index += 2) {
			rendered = rendered.replace("{{" + values[index] + "}}", values[index + 1]);
		}
		return rendered;
	}
}
