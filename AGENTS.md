# AGENTS

## 语言

- 除课程正式提交内容外，项目说明和协作回复默认使用简体中文。
- 最终课程报告按作业要求使用英文。

## 项目依据

本项目用于完成《Software Architecture (2026) Assignment 2》。

- 权威作业要求：[docs/task.md](docs/task.md)
- 中文理解版：[docs/task-cn.md](docs/task-cn.md)，仅用于辅助理解
- 原始 PDF：[docs/2026SoftArch-assignment2.pdf](docs/2026SoftArch-assignment2.pdf)
- Agent 工程：[hotel-pricing-agent](hotel-pricing-agent)
- 运行与交互说明：[hotel-pricing-agent/README.md](hotel-pricing-agent/README.md)
- Agent 内置先验知识：[hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md](hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md)

后续任务理解、报告结构和验收标准以 [docs/task.md](docs/task.md) 为准。

## 已定方案

- 完成方式：Option 2，Single-Agent: Sequential Reasoning and Self-Reflection。
- 使用模型：Qwen3-Max。
- 技术路线：Spring AI Alibaba + Spring Boot + Maven。
- 工作方式：人工与 single-agent CLI 逐轮交互，不由程序自动批量生成四轮 ADD。
- 设计方法：ADD 3.0。

## 关键约束

1. Agent 默认只注入 `knowledge/prior-knowledge.md`，内容限于 ADD 3.0、Hotel Pricing System case study 和四轮迭代计划。
2. System prompt 必须包含 prior knowledge、role prompt、self-reflection / self-verification 要求。
3. 不引入外部领域知识、few-shot 示例、手工演示输出或额外需求扩展。
4. 决策必须能追溯到系统指令、ADD steps、drivers、concerns 或 constraints。
5. 架构视图使用 Mermaid 或 PlantUML 代码。
6. 不得在代码、文档或日志中硬编码 API Key、访问令牌或私密配置。

## 正式交互流程

正式 agent 会话按 [hotel-pricing-agent/README.md](hotel-pricing-agent/README.md) 执行：

1. Initial ADD Input Review：单独完成 ADD Step 1。
2. Iteration 1-4：分别完成对应迭代的 ADD Step 2 到 Step 7。
3. 每轮输出应包含 self-reflection / self-verification。
4. Conversation log 应包含 system prompt、人工输入、模型输出、时间戳，并在结尾记录本次 agent 会话总 token。

## 交付物

最终需要准备：

1. Source code：Spring AI Alibaba single-agent 程序。
2. Complete conversation log：覆盖 Initial ADD Input Review 和四轮 ADD 迭代。
3. Report：英文，不超过 30 页 A4，按 [docs/task.md](docs/task.md) Appendix 模板组织，并包含 agent 交互成本分析。
