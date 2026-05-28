# Stage

## 当前阶段

当前处于 **搭建 agent 阶段**。

本阶段目标是使用 **Spring AI Alibaba** 搭建一个面向作业要求的 **Qwen3-Max single-agent**，为后续执行 ADD 3.0 四轮迭代、保存对话日志和生成英文报告素材做准备。

## 阶段依据

- 项目级约束：[AGENTS.md](AGENTS.md)
- 作业英文原文：[docs/task.md](docs/task.md)
- 中文理解版：[docs/task-cn.md](docs/task-cn.md)

本阶段实现时，仍以 [docs/task.md](docs/task.md) 为权威依据。

## 阶段目标

1. 搭建 Spring AI Alibaba 项目骨架。
2. 接入 Qwen3-Max 模型调用能力。
3. 实现 single-agent 主流程，而不是直接单次调用 LLM。
4. 固化 system prompt、iteration prompt 和 self-reflection prompt 的生成方式。
5. 将 [docs/task.md](docs/task.md) 中允许使用的 prior knowledge 注入 agent。
6. 支持按四轮 ADD 迭代顺序运行。
7. 记录完整交互日志，包括时间戳、输入、模型输出、self-reflection、耗时和 token 使用量。
8. 为后续报告生成保留结构化输出。

## 阶段范围

本阶段需要重点完成：

- Spring Boot / Spring AI Alibaba 基础工程。
- Qwen3-Max 配置与调用封装。
- agent 编排逻辑。
- prompt 构造逻辑。
- prior knowledge 加载逻辑。
- conversation log 保存逻辑。
- token 和耗时统计入口。
- 一次可验证的本地运行流程。

本阶段暂不要求完成：

- 最终英文报告全文。
- 最终 ADD 架构设计结果定稿。
- 小组成员个人反思内容。
- 最终提交包整理。

## Agent 行为要求

agent 必须满足 [docs/task.md](docs/task.md) 中 Option 2 的要求：

1. 使用 single-agent 方式完成任务。
2. 通过 sequential reasoning 执行四轮 ADD 迭代。
3. 每轮迭代后进行 self-reflection / self-verification。
4. 只使用作业给定 prior knowledge，不引入外部领域知识。
5. 不使用 few-shot 示例或手工演示输出。
6. 不进行额外任务重新解释或需求扩展。
7. 输出架构视图时使用 Mermaid 或 PlantUML 代码。
8. 决策规则必须能追溯到 system instructions、ADD 步骤、drivers、concerns 或 constraints。

## 推荐代码职责划分

后续目录可根据实际 Spring Boot 项目结构调整，但建议保持以下职责边界：

- `config`：模型、客户端、prompt 模板和运行参数配置。
- `agent`：single-agent 主流程，包括四轮 ADD 迭代编排与 self-reflection。
- `prompt`：system prompt、iteration prompt、reflection prompt 等提示词构造。
- `knowledge`：从 [docs/task.md](docs/task.md) 抽取或整理 prior knowledge，不额外扩充外部知识。
- `logging`：完整交互日志记录，包含时间戳、输入、模型输出、self-reflection、token 统计和耗时。
- `report`：报告素材或报告生成辅助逻辑。

## 配置与安全要求

- 不要在代码中硬编码 API Key、访问令牌或私密配置。
- 模型名称、API Key、base URL 等运行配置应通过环境变量、本地配置文件或 Spring 配置机制注入。
- 本地私密配置文件不应提交到仓库。
- 需要记录实际使用模型为 `Qwen3-Max`。
- 如果模型调用暂时不可用，应保证 agent 编排、prompt 构造和日志写入逻辑仍可通过 mock 或 dry-run 方式验证。

## 日志要求

conversation log 应为后续交付物服务，至少记录：

- 运行开始和结束时间。
- 使用模型。
- 每轮 ADD iteration 名称。
- human / system / agent 输入内容。
- Qwen3-Max 原始输出。
- self-reflection / self-verification 输出。
- 每次调用耗时。
- token 消耗，如果 API 响应可获得。
- 错误信息和重试信息，如果发生。

日志格式应优先选择 Markdown、JSON 或 JSONL 中的一种，保证后续可以直接引用或自动处理。

## 阶段验收标准

本阶段完成时，应满足：

1. 可以启动 Spring AI Alibaba agent 程序。
2. 可以明确配置并调用或模拟调用 Qwen3-Max。
3. 可以按四轮 ADD 迭代顺序生成请求。
4. 每轮迭代都包含 self-reflection / self-verification 步骤。
5. 可以生成完整 conversation log 文件。
6. 日志中包含时间戳和阶段信息。
7. 代码中没有硬编码密钥。
8. README 或运行说明中能说明如何启动 agent。

## 下一阶段预告

agent 搭建完成后，建议进入 **运行 ADD 迭代阶段**：

1. 使用真实 Qwen3-Max 执行四轮 ADD 迭代。
2. 保存完整 conversation log。
3. 检查输出是否违反外部知识限制。
4. 整理 ADD 输出结果。
5. 准备英文报告素材。
