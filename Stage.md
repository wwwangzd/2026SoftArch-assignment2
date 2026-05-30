# Stage

## 当前阶段状态

当前处于 **人工交互式 agent 工作流调整阶段**。

本阶段目标是使用 **Spring AI Alibaba** 搭建面向作业要求的 **Qwen3-Max single-agent 环境**。正式作业过程由人工与 agent 逐轮交互完成，程序负责注入系统提示词、调用模型、打印回复并保存完整 conversation log。

## 本阶段已完成

已创建 Spring Boot + Maven 工程：[hotel-pricing-agent](hotel-pricing-agent)。

已完成的初始化内容：

1. 添加 Spring AI Alibaba 相关依赖。
2. 配置 `qwen3-max` 模型入口，默认使用 `dry-run` 模式，真实运行时切换到 `dashscope`。
3. 建立 `config / agent / prompt / knowledge / logging / report` 基础包结构。
4. 实现人工交互式 single-agent CLI，不做一次性直接问答，也不自动批量执行四轮 ADD。
5. prior knowledge 中保留四轮 ADD 迭代计划：
   - Iteration 1: Establishing an Overall System Structure
   - Iteration 2: Identifying Structures to Support Primary Functionality
   - Iteration 3: Addressing Reliability and Availability Quality Attributes
   - Iteration 4: Addressing Development and Operations
6. 实现 system prompt；每轮具体任务由人工输入，self-reflection / self-verification 作为每轮输出的一部分由模型完成。
7. 已将 agent 先验知识移动到 [hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md](hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md)，作为 agent 工程内置能力资源，默认只注入：
   - ADD 3.0
   - Hotel Pricing System case study
   - 四轮迭代计划
8. 实现 conversation log 输出，包含系统提示词、人工输入、模型输出和时间戳。
9. 增加 dry-run client，用于无 API Key 时验证交互流程。
10. 增加 DashScope client 入口，用于后续真实调用 Qwen3-Max。
11. 增加运行说明：[hotel-pricing-agent/README.md](hotel-pricing-agent/README.md)。

## 已验证内容

在 [hotel-pricing-agent](hotel-pricing-agent) 下已通过：

```bash
mvn test
mvn spring-boot:run
```

验证结果：

- Spring Boot 测试通过。
- dry-run 模式可启动并接受人工输入。
- 可读取 agent classpath 内置知识 `knowledge/prior-knowledge.md`。
- 可由人工逐轮输入 ADD 迭代请求。
- system prompt 要求每轮设计输出包含 self-reflection / self-verification。
- 可生成 conversation log 到 `logs/`。
- 代码中没有硬编码 API Key。
- 已验证 `DASHSCOPE_API_KEY` 在本地 shell 中存在。
- 已执行过 1 轮真实 Qwen3-Max smoke test，确认 DashScope 调用链路可用。
- 已确认不采用程序自动执行四轮 ADD；下一步应由人工发起正式四轮交互。

说明：`logs/` 已在根目录 `.gitignore` 中忽略，避免测试日志混入提交。后续真实运行产生的最终日志应单独整理到交付材料中。

## 当前保留约束

后续继续遵守 [AGENTS.md](AGENTS.md) 和 [docs/task.md](docs/task.md)：

1. 只能使用作业给定 prior knowledge；默认输入为 agent classpath 资源 `knowledge/prior-knowledge.md`。
2. 正式四轮 ADD 必须由人工逐轮输入请求，不由程序自动批处理。
3. 不引入外部领域知识。
4. 不使用 few-shot 示例或手工演示输出。
5. 不额外扩展或重解释需求。
6. 决策规则必须可追溯到 system instructions、ADD steps、drivers、concerns 或 constraints。
7. 架构视图必须使用 Mermaid 或 PlantUML。
8. 每轮迭代输出中必须包含 self-reflection / self-verification。

## 下一阶段

下一阶段建议进入 **人工交互测试与正式四轮对话阶段**。

主要任务：

1. 人工确认 [hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md](hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md) 是否只包含作业要求注入的三类先验知识。
2. 人工确认 agent 工程中不出现不必要的作业描述；作业语境只保留在根目录协作文档中。
3. 人工确认 system prompt 是否只包含 task.md Option 2 明文要求。
4. 确认 agent 工作方式是否符合 single-agent sequential reasoning + self-reflection：
   - 程序负责维护单个 agent 会话环境；
   - 人工负责逐轮输入 ADD 迭代任务和必要追问；
   - 模型根据 system instruction 在每轮输出内执行任务分解、步骤规划和自我验证；
   - conversation log 保存完整人工输入和模型输出。
5. 确认无误后，使用环境变量启动真实交互式会话：

```bash
export AGENT_MODEL_MODE=dashscope
cd hotel-pricing-agent
mvn spring-boot:run
```

6. 在终端中人工输入四轮 ADD 请求，每条消息以 `/send` 提交，最终以 `/exit` 结束。
7. 保存真实 Qwen3-Max 生成的完整 conversation log。
8. 检查每轮输出是否违反作业限制。
9. 如有外部知识、缺少图、缺少 ADD 步骤或 self-reflection 不充分，应在同一会话中人工要求 agent 修正，并保留日志。
10. 从最终日志中整理 ADD 输出结果。
11. 准备英文报告初稿，结构按 [docs/task.md](docs/task.md) Appendix。
12. 单独整理 interaction cost analysis，记录完成作业过程中使用的 AI 工具与成本；它不是 agent conversation log 的一部分。

## 下一阶段验收标准

真实运行阶段完成时，应得到：

1. 一份完整真实 conversation log，覆盖四轮 ADD 迭代和 self-reflection。
2. 一份可用于报告的 ADD 输出结果材料。
3. 一份独立的 interaction cost analysis 草稿，用于报告而不是 conversation log：
   - 作业完成方式：通过 Codex 协助搭建并使用 Single-Agent 完成 ADD 设计。
   - 使用的 AI/LLM 工具：Codex；Qwen3-Max agent 会话。
   - 人工交互次数：分别统计与 Codex 的协作轮次、与 Qwen3-Max agent 的正式交互轮次。
   - Token 消耗：尽量分别记录 Codex 协作消耗与 Qwen3-Max agent 会话消耗；若无法精确获得，应在报告中说明估算方式或限制。
   - 时间成本：记录从搭建、调试、正式交互到整理报告的人工时间。
4. 对模型输出的合规检查记录。
