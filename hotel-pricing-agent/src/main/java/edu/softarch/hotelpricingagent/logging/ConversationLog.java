package edu.softarch.hotelpricingagent.logging;

import java.nio.file.Path;
import java.time.Instant;

public record ConversationLog(Path path, Instant startedAt) {
}
