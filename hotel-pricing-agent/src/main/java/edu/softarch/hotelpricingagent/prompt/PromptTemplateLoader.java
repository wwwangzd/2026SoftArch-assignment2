package edu.softarch.hotelpricingagent.prompt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import edu.softarch.hotelpricingagent.config.AgentProperties;

@Service
public class PromptTemplateLoader {

	private final AgentProperties properties;

	public PromptTemplateLoader(AgentProperties properties) {
		this.properties = properties;
	}

	public String loadSystemPromptTemplate() {
		return read("system prompt", properties.getSystemPromptResource());
	}

	private String read(String name, org.springframework.core.io.Resource resource) {
		try {
			return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
		}
		catch (IOException ex) {
			throw new IllegalStateException("Cannot read " + name + " resource: " + resource, ex);
		}
	}
}
