package edu.softarch.hotelpricingagent.knowledge;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import edu.softarch.hotelpricingagent.config.AgentProperties;

@Service
public class AgentKnowledgeLoader {

	private final AgentProperties properties;

	public AgentKnowledgeLoader(AgentProperties properties) {
		this.properties = properties;
	}

	public String loadPriorKnowledge() {
		try {
			return StreamUtils.copyToString(properties.getKnowledgeResource().getInputStream(), StandardCharsets.UTF_8);
		}
		catch (IOException ex) {
			throw new IllegalStateException("Cannot read agent knowledge resource: " + properties.getKnowledgeResource(), ex);
		}
	}
}
