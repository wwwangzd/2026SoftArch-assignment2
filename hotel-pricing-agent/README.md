# ADD Architecture Agent

Spring AI Alibaba single-agent environment for human-led ADD-based architecture design.

## Start The Agent

From the repository root:

```bash
cd hotel-pricing-agent
export AGENT_MODEL_MODE=dashscope
mvn spring-boot:run
```

If your shell already exports `DASHSCOPE_API_KEY`, the application will use it. Otherwise set:

```bash
export AI_DASHSCOPE_API_KEY=your_api_key
```

The default model is `qwen3-max`. Each message is submitted by typing the prompt, then a line containing only `/send`. End the session with `/exit`.

The conversation uses Spring AI in-memory chat memory for the current process. The conversation log is written to `../logs/`. It records the system prompt once, then records each submitted human message and model response with timestamps.

## Iteration Prompts

### Iteration 1

```text
Complete Iteration 1: Establishing an Overall System Structure.

Use ADD 3.0 to produce the result for this iteration. Cover ADD Step 2 to ADD Step 7. Generate any architecture views using Mermaid or PlantUML code. Use only the provided prior knowledge and follow the system instructions, including self-reflection and self-verification.
```

### Iteration 2

```text
Complete Iteration 2: Identifying Structures to Support Primary Functionality.

Use the previous iteration result as context. Use ADD 3.0 to produce the result for this iteration. Cover ADD Step 2 to ADD Step 7. Generate any architecture views using Mermaid or PlantUML code. Use only the provided prior knowledge and follow the system instructions, including self-reflection and self-verification.
```

### Iteration 3

```text
Complete Iteration 3: Addressing Reliability and Availability Quality Attributes.

Use the previous iteration results as context. Use ADD 3.0 to produce the result for this iteration. Cover ADD Step 2 to ADD Step 7. Generate any architecture views using Mermaid or PlantUML code. Use only the provided prior knowledge and follow the system instructions, including self-reflection and self-verification.
```

### Iteration 4

```text
Complete Iteration 4: Addressing Development and Operations.

Use the previous iteration results as context. Use ADD 3.0 to produce the result for this iteration. Cover ADD Step 2 to ADD Step 7. Generate any architecture views using Mermaid or PlantUML code. Use only the provided prior knowledge and follow the system instructions, including self-reflection and self-verification.
```

Do not commit API keys or local private configuration files.
