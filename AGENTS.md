# AGENTS

## 项目定位

本项目用于完成《Software Architecture (2026) Assignment 2》。

- 作业要求英文原文：[docs/task.md](docs/task.md)
- 作业要求中文理解版：[docs/task-cn.md](docs/task-cn.md)
- 作业原始 PDF：[docs/2026SoftArch-assignment2.pdf](docs/2026SoftArch-assignment2.pdf)
- 当前阶段说明：[Stage.md](Stage.md)

后续协作时，**以 [docs/task.md](docs/task.md) 为权威任务依据**；[docs/task-cn.md](docs/task-cn.md) 仅用于理解作业需求，不作为最终报告或 agent 输入的权威文本。

## 已确定方案

- 作业完成方式：**Option 2. Single-Agent: Sequential Reasoning and Self-Reflection**。
- 使用模型：**Qwen3-Max**。
- 技术路线：使用 **Spring AI Alibaba** 搭建单智能体程序。
- 设计方法：使用 **ADD 3.0** 完成 Hotel Pricing System 的软件体系结构设计。
- 报告语言：最终提交报告必须使用英文。

## 项目级约束

详细要求以 [docs/task.md](docs/task.md) 为准。当前项目必须长期遵守以下约束：

1. 创建一个 single-agent，而不是直接一次性向 LLM 提问。
2. agent 必须使用 ADD 3.0 完成 Hotel Pricing System 的架构设计。
3. agent 的系统指令必须包含 prior knowledge、role prompt、self-reflection。
4. 只能使用 [docs/task.md](docs/task.md) 中提供的 prior knowledge：
   - ADD 3.0；
   - Hotel Pricing System case study；
   - 四轮迭代计划。
5. prompt 中不允许加入 few-shot 示例或手工编写的演示输出。
6. 不允许引入超出作业材料的外部领域知识。
7. 不允许对任务进行额外重新解释或需求扩展。
8. 所有决策规则必须显式来源于系统指令。
9. 迭代过程中产生的视图必须使用 Mermaid 或 PlantUML 代码生成。
10. agent 可以进行任务分解、推理步骤规划和自我验证，但这些行为必须来源于系统指令，且不得引入外部知识。

## ADD 迭代计划

agent 需要按 [docs/task.md](docs/task.md) 中要求完成四轮迭代：

1. Iteration 1: Establishing an Overall System Structure
2. Iteration 2: Identifying Structures to Support Primary Functionality
3. Iteration 3: Addressing Reliability and Availability Quality Attributes
4. Iteration 4: Addressing Development and Operations

建议将 ADD Step 1 作为全局输入审查；每轮迭代输出 ADD Step 2 到 ADD Step 7 的结果。

## 必须产出的交付物

按照 [docs/task.md](docs/task.md)，最终需要准备：

1. **Source code**，15 分：
   - Spring AI Alibaba single-agent 程序；
   - prompt 构造逻辑；
   - 四轮 ADD 迭代执行逻辑；
   - 日志和统计逻辑。
2. **Complete conversation log**，15 分：
   - 与 Qwen3-Max 交互的完整日志；
   - 必须包含时间戳；
   - 必须覆盖四轮 ADD 迭代；
   - 应包含每轮 self-reflection / self-verification 输出。
3. **Report**，20 分：
   - 必须英文编写；
   - 不超过 30 页 A4；
   - 按 [docs/task.md](docs/task.md) 附录模板组织；
   - 包含 ADD 输出结果、交互成本分析和个人反思。

## 协作注意事项

- 当前阶段的具体目标、实现范围和验收标准记录在 [Stage.md](Stage.md)。
- 修改作业要求、prompt 规则、报告结构前，应先对照 [docs/task.md](docs/task.md)。
- 不要把 [docs/task-cn.md](docs/task-cn.md) 中的中文理解内容直接作为最终英文报告内容。
- 生成架构设计时，应明确说明每个设计决策对应的 drivers、concerns、constraints 或 ADD 步骤来源。
- Mermaid 或 PlantUML 图应作为代码块保存，便于报告复用。
- 如果模型输出疑似使用了外部知识，应通过 self-reflection 标记并重新生成。
- 后续所有文档说明默认使用简体中文；最终报告、提交给课程的正式内容按作业要求使用英文。
