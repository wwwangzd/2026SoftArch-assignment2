# ADD Architecture Agent

Spring AI Alibaba + Qwen3-Max single-agent CLI for human-led ADD architecture design.

## Run

From the repository root:

```bash
cd hotel-pricing-agent
export AGENT_MODEL_MODE=dashscope
mvn spring-boot:run
```

The application reads `DASHSCOPE_API_KEY` first. If needed, set:

```bash
export AI_DASHSCOPE_API_KEY=your_api_key
```

Each message is submitted by typing the prompt, then a line containing only `/send`. End the session with `/exit`.

## Log

Conversation logs are written to `../logs/`. A log records:

- system prompt;
- each submitted user prompt;
- each model output;
- timestamps;
- final session total token count when provider usage metadata is available.

## Interaction Prompts

The CLI displays each stage name before input. Submit the following prompts in order.

### Initial ADD Input Review

```text
Perform ADD Step 1 for the overall Hotel Pricing System.

Review the provided inputs and identify the requirements considered architectural drivers. Keep this as a separate global input review before the four ADD design iterations. Do not produce iteration design results in this response. Use only the provided prior knowledge and follow the system instructions, including self-reflection and self-verification.
```

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

After the fourth iteration model response is complete, type `/exit`.

Do not commit API keys or local private configuration files.
