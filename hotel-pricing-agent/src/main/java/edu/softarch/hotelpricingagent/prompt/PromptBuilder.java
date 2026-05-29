package edu.softarch.hotelpricingagent.prompt;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.softarch.hotelpricingagent.agent.AddIteration;

@Service
public class PromptBuilder {

	private final PromptTemplateLoader templateLoader;

	public PromptBuilder(PromptTemplateLoader templateLoader) {
		this.templateLoader = templateLoader;
	}

	public String buildSystemPrompt(String priorKnowledge) {
		return render(templateLoader.load().systemPrompt(), "priorKnowledge", priorKnowledge);
	}

	public String buildIterationPrompt(AddIteration iteration, List<String> previousIterationSummaries) {
		String previousContext = previousIterationSummaries.isEmpty()
				? "No previous iteration output exists."
				: String.join("\n\n", previousIterationSummaries);
		PromptTemplates templates = templateLoader.load();
		return render(templates.iterationPrompt(),
				"iterationLabel", iteration.label(),
				"selfVerificationPrompt", templates.selfVerificationPrompt(),
				"previousContext", previousContext);
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
