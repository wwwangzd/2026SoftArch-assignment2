# AGENTS

## 项目依据

本项目用于完成《Software Architecture (2026) Assignment 2》。

- 权威作业要求：[docs/task.md](docs/task.md)
- agent 内置能力知识：[hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md](hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md)
- 中文理解版：[docs/task-cn.md](docs/task-cn.md)
- 原始 PDF：[docs/2026SoftArch-assignment2.pdf](docs/2026SoftArch-assignment2.pdf)
- 当前阶段：[Stage.md](Stage.md)
- agent 工程：[hotel-pricing-agent](hotel-pricing-agent)
- 运行说明：[hotel-pricing-agent/README.md](hotel-pricing-agent/README.md)

后续协作以 [docs/task.md](docs/task.md) 为准；[docs/task-cn.md](docs/task-cn.md) 只用于理解，不作为最终报告或 agent 输入的权威文本。

## 已定方案

- 完成方式：Option 2，Single-Agent: Sequential Reasoning and Self-Reflection。
- 使用模型：Qwen3-Max。
- 技术路线：Spring AI Alibaba + Spring Boot + Maven。
- 当前工程：已创建 `hotel-pricing-agent`，作为人工交互式 single-agent 环境使用。
- 设计方法：ADD 3.0。
- 报告语言：英文。

## 硬性约束

1. 必须实现 single-agent，不做一次性直接问答。
2. 作业正式过程必须由人工与 agent 逐轮交互完成，不由程序自动批量执行四轮 ADD。
3. agent 默认只注入自身资源目录中的 `knowledge/prior-knowledge.md`，内容限于 ADD 3.0、Hotel Pricing System 和四轮迭代计划。
4. system prompt 必须包含 prior knowledge、role prompt、self-reflection。
5. 不允许引入外部领域知识、few-shot 示例、手工演示输出或额外需求扩展。
6. 所有决策规则必须能追溯到系统指令、ADD 步骤、drivers、concerns 或 constraints。
7. 架构视图必须使用 Mermaid 或 PlantUML 代码。
8. 每轮 ADD 迭代输出中必须包含 self-reflection / self-verification。
9. 不得在代码中硬编码 API Key、访问令牌或私密配置。

## 交付物

最终需要准备：

1. Source code：Spring AI Alibaba single-agent 程序、prompt 构造、人工交互式四轮 ADD 支持和 conversation log 记录逻辑。
2. Complete conversation log：包含人工输入、时间戳、四轮迭代、Qwen3-Max 输出和 self-reflection。
3. Report：英文，不超过 30 页 A4，按 [docs/task.md](docs/task.md) 附录模板组织；其中 interaction cost analysis 单独记录完成作业使用的 AI 工具与成本，不写入 conversation log。

## 协作约定

- 当前阶段目标、范围和验收标准写在 [Stage.md](Stage.md)。
- 运行 agent 前先阅读 [hotel-pricing-agent/README.md](hotel-pricing-agent/README.md)。
- 修改 prompt、报告结构或任务理解前，先对照 [docs/task.md](docs/task.md)。
- 修改 agent 内置知识前，先确认是否仍只包含作业要求注入的 prior knowledge。
- 后续说明文档默认使用简体中文；课程正式提交内容按作业要求使用英文。
