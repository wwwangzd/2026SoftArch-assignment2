package edu.softarch.hotelpricingagent.agent;

import java.nio.file.Path;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "agent.run-on-startup", havingValue = "true", matchIfMissing = true)
public class AgentRunner implements CommandLineRunner {

	private final AgentOrchestrator orchestrator;

	public AgentRunner(AgentOrchestrator orchestrator) {
		this.orchestrator = orchestrator;
	}

	@Override
	public void run(String... args) {
		Path logPath = orchestrator.run();
		System.out.println("Agent initialization workflow completed.");
		System.out.println("Conversation log: " + logPath.toAbsolutePath().normalize());
	}
}
