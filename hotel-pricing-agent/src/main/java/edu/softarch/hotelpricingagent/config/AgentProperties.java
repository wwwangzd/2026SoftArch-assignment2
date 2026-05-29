package edu.softarch.hotelpricingagent.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "agent")
public class AgentProperties {

	private boolean runOnStartup = true;

	private String modelMode = "dry-run";

	private String modelName = "qwen3-max";

	private Resource knowledgeResource;

	private Resource systemPromptResource;

	private Resource iterationPromptResource;

	private Resource selfVerificationPromptResource;

	private Path logDir = Path.of("../logs");

	private int maxIterations = 4;

	public boolean isRunOnStartup() {
		return runOnStartup;
	}

	public void setRunOnStartup(boolean runOnStartup) {
		this.runOnStartup = runOnStartup;
	}

	public String getModelMode() {
		return modelMode;
	}

	public void setModelMode(String modelMode) {
		this.modelMode = modelMode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Resource getKnowledgeResource() {
		return knowledgeResource;
	}

	public void setKnowledgeResource(Resource knowledgeResource) {
		this.knowledgeResource = knowledgeResource;
	}

	public Resource getSystemPromptResource() {
		return systemPromptResource;
	}

	public void setSystemPromptResource(Resource systemPromptResource) {
		this.systemPromptResource = systemPromptResource;
	}

	public Resource getIterationPromptResource() {
		return iterationPromptResource;
	}

	public void setIterationPromptResource(Resource iterationPromptResource) {
		this.iterationPromptResource = iterationPromptResource;
	}

	public Resource getSelfVerificationPromptResource() {
		return selfVerificationPromptResource;
	}

	public void setSelfVerificationPromptResource(Resource selfVerificationPromptResource) {
		this.selfVerificationPromptResource = selfVerificationPromptResource;
	}

	public Path getLogDir() {
		return logDir;
	}

	public void setLogDir(Path logDir) {
		this.logDir = logDir;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}
}
