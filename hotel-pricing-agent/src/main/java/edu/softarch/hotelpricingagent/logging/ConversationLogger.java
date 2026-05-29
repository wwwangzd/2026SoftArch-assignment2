package edu.softarch.hotelpricingagent.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import edu.softarch.hotelpricingagent.agent.AgentModelRequest;
import edu.softarch.hotelpricingagent.agent.AgentModelResponse;
import edu.softarch.hotelpricingagent.config.AgentProperties;

@Service
public class ConversationLogger {

	private static final DateTimeFormatter FILE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyyMMdd-HHmmss")
			.withZone(ZoneId.systemDefault());

	private static final DateTimeFormatter LOG_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME
			.withZone(ZoneId.systemDefault());

	private final AgentProperties properties;

	public ConversationLogger(AgentProperties properties) {
		this.properties = properties;
	}

	public ConversationLog start(String modelName, String modelMode) {
		try {
			Files.createDirectories(properties.getLogDir());
			Instant startedAt = Instant.now();
			var path = properties.getLogDir().resolve("conversation-" + FILE_TIME_FORMATTER.format(startedAt) + ".md");
			String header = """
					# Conversation Log

					- Started at: %s
					- Model: %s
					- Model mode: %s
					- Knowledge resource: %s

					""".formatted(formatTime(startedAt), modelName, modelMode, properties.getKnowledgeResource());
			Files.writeString(path, header, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
			return new ConversationLog(path, startedAt);
		}
		catch (IOException ex) {
			throw new IllegalStateException("Cannot create conversation log in " + properties.getLogDir(), ex);
		}
	}

	public void appendInteraction(ConversationLog log, AgentModelRequest request, AgentModelResponse response) {
		String text = """
				## %s

				- Timestamp: %s
				- Elapsed: %d ms
				- Prompt tokens: %s
				- Completion tokens: %s

				### System Prompt

				```text
				%s
				```

				### User Prompt

				```text
				%s
				```

				### Model Output

				```text
				%s
				```

				""".formatted(
				request.callName(),
				formatTime(Instant.now()),
				response.elapsedMillis(),
				formatNullable(response.promptTokens()),
				formatNullable(response.completionTokens()),
				request.systemPrompt(),
				request.userPrompt(),
				response.content());
		append(log, text);
	}

	public void finish(ConversationLog log) {
		append(log, "- Finished at: " + formatTime(Instant.now()) + "\n");
	}

	private void append(ConversationLog log, String text) {
		try {
			Files.writeString(log.path(), text, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
		}
		catch (IOException ex) {
			throw new IllegalStateException("Cannot append conversation log: " + log.path(), ex);
		}
	}

	private String formatNullable(Integer value) {
		return value == null ? "N/A" : value.toString();
	}

	private String formatTime(Instant instant) {
		return LOG_TIME_FORMATTER.format(instant);
	}
}
