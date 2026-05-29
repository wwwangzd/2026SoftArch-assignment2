# Stage

## 当前阶段状态

当前处于 **agent 工程边界与 prompt 语义调整阶段**。

本阶段目标是使用 **Spring AI Alibaba** 搭建面向作业要求的 **Qwen3-Max single-agent**，并为后续执行 ADD 3.0 四轮迭代、保存 conversation log、整理英文报告素材做准备。

## 本阶段已完成

已创建 Spring Boot + Maven 工程：[hotel-pricing-agent](hotel-pricing-agent)。

已完成的初始化内容：

1. 添加 Spring AI Alibaba 相关依赖。
2. 配置 `qwen3-max` 模型入口，默认使用 `dry-run` 模式。
3. 建立 `config / agent / prompt / knowledge / logging / report` 基础包结构。
4. 实现 single-agent 编排骨架，不做一次性直接问答。
5. 实现四轮 ADD 迭代顺序：
   - Iteration 1: Establishing an Overall System Structure
   - Iteration 2: Identifying Structures to Support Primary Functionality
   - Iteration 3: Addressing Reliability and Availability Quality Attributes
   - Iteration 4: Addressing Development and Operations
6. 实现 system prompt 和 iteration prompt；self-reflection / self-verification 作为每轮迭代输出的一部分由模型完成。
7. 已将 agent 先验知识移动到 [hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md](hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md)，作为 agent 工程内置能力资源，默认只注入：
   - ADD 3.0
   - Hotel Pricing System case study
   - 四轮迭代计划
8. 实现 conversation log 输出，包含时间戳、prompt、模型输出、耗时和 token 字段。
9. 增加 dry-run client，用于无 API Key 时验证流程。
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
- dry-run 模式可启动。
- 可读取 agent classpath 内置知识 `knowledge/prior-knowledge.md`。
- 可按四轮 ADD 迭代生成请求。
- 每轮迭代输出内包含 self-reflection / self-verification 要求。
- 可生成 conversation log 到 `logs/`。
- 代码中没有硬编码 API Key。
- 已验证 `DASHSCOPE_API_KEY` 在本地 shell 中存在。
- 已执行过 1 轮真实 Qwen3-Max smoke test，确认 DashScope 调用链路可用。
- 暂停执行完整 4 轮真实运行，等待进一步确认 prompt、先验知识边界和 reflection 形式。

说明：`logs/` 已在根目录 `.gitignore` 中忽略，避免 dry-run 日志混入提交。后续真实运行产生的最终日志应单独整理到交付材料中。

## 当前保留约束

后续继续遵守 [AGENTS.md](AGENTS.md) 和 [docs/task.md](docs/task.md)：

1. 只能使用作业给定 prior knowledge；默认输入为 agent classpath 资源 `knowledge/prior-knowledge.md`。
2. 不引入外部领域知识。
3. 不使用 few-shot 示例或手工演示输出。
4. 不额外扩展或重解释需求。
5. 决策规则必须可追溯到 system instructions、ADD steps、drivers、concerns 或 constraints。
6. 架构视图必须使用 Mermaid 或 PlantUML。
7. 每轮迭代输出中必须包含 self-reflection / self-verification。

## 下一阶段

下一阶段建议先进入 **prompt 确认阶段**，确认后再进入真实运行 ADD 迭代阶段。

主要任务：

1. 人工确认 [hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md](hotel-pricing-agent/src/main/resources/knowledge/prior-knowledge.md) 是否只包含作业要求注入的三类先验知识。
2. 人工确认 agent 工程中不出现不必要的作业描述；作业语境只保留在根目录协作文档中。
3. 人工确认 `PromptBuilder` 中的 system prompt 是否只包含 task.md Option 2 明文要求。
4. 确认 agent 编排方式是否符合 single-agent sequential reasoning + self-reflection：
   - 程序负责按四轮顺序调用；
   - 模型根据 system instruction 在每轮输出内执行任务分解、步骤规划和自我验证；
   - conversation log 保存完整交互过程。
5. 确认无误后，使用环境变量启动真实模型调用：

```bash
export AGENT_MODEL_MODE=dashscope
cd hotel-pricing-agent
mvn spring-boot:run
```

6. 保存真实 Qwen3-Max 生成的完整 conversation log。
7. 检查每轮输出是否违反作业限制。
8. 如有外部知识、缺少图、缺少 ADD 步骤或 self-reflection 不充分，调整 prompt 后重新运行。
9. 从最终日志中整理 ADD 输出结果。
10. 准备英文报告初稿，结构按 [docs/task.md](docs/task.md) Appendix。

## 下一阶段验收标准

真实运行阶段完成时，应得到：

1. 一份完整真实 conversation log，覆盖四轮 ADD 迭代和 self-reflection。
2. 一份可用于报告的 ADD 输出结果材料。
3. interaction cost analysis 所需信息：
   - 完成方式：Single-Agent
   - 使用模型：Qwen3-Max
   - 人工交互轮次
   - token 消耗，如果 API 返回
   - 总耗时
4. 对模型输出的合规检查记录。
