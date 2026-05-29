# ADD Architecture Agent

Spring AI Alibaba single-agent project for ADD-based architecture design.

## Run in dry-run mode

Dry-run mode validates prompt generation, ADD iteration orchestration, and conversation logging without calling Qwen3-Max.

```bash
mvn spring-boot:run
```

The agent reads prior knowledge from `classpath:knowledge/prior-knowledge.md`.
The conversation log is written to `../logs/`.

`../logs/` is ignored by default because dry-run logs are generated artifacts. Copy or move the final real conversation log into the final submission materials when needed.

## Run with DashScope

Set the API key and switch the model mode:

```bash
export AI_DASHSCOPE_API_KEY=your_api_key
export AGENT_MODEL_MODE=dashscope
mvn spring-boot:run
```

If your shell already exports `DASHSCOPE_API_KEY`, the application will use it as a fallback.

Default model name is `qwen3-max`. Override it with:

```bash
export AGENT_MODEL_NAME=qwen3-max
```

For a low-cost smoke test, run only the first ADD iteration:

```bash
export AGENT_MODEL_MODE=dashscope
export AGENT_MAX_ITERATIONS=1
mvn spring-boot:run
```

Do not commit API keys or local private configuration files.
