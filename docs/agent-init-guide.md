# Agent 项目创建与初始化指南

本文用于在 VS Code 中初始化本作业的 Spring AI Alibaba agent 项目。

## 1. 环境检查

在项目根目录执行：

```bash
java -version
mvn -version
```

要求：

- JDK 17 或以上。
- Maven 可用。
- 本地已有 `.m2` 目录即可，无需额外配置。

## 2. 创建 Spring Boot 项目

在 VS Code 中：

1. 打开当前作业目录。
2. 按 `Cmd + Shift + P`。
3. 选择 `Spring Initializr: Create a Maven Project`。
4. 建议配置：
   - Language: `Java`
   - Spring Boot: 选择稳定的 `3.x`
   - Group Id: `edu.softarch`
   - Artifact Id: `hotel-pricing-agent`
   - Packaging: `Jar`
   - Java: `17`
5. 依赖先选择：
   - `Spring Web`
   - `Spring Boot DevTools` 可选
   - `Lombok` 可选

建议将生成的项目放在当前目录下的 `hotel-pricing-agent/`。

## 3. 添加 Spring AI Alibaba 依赖

在 `hotel-pricing-agent/pom.xml` 中添加依赖：

```xml
<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-agent-framework</artifactId>
    <version>1.1.2.0</version>
</dependency>

<dependency>
    <groupId>com.alibaba.cloud.ai</groupId>
    <artifactId>spring-ai-alibaba-starter-dashscope</artifactId>
    <version>1.1.2.0</version>
</dependency>
```

如版本不可用，再根据 Spring AI Alibaba 官方文档调整。

## 4. 配置模型

在 `src/main/resources/application.yml` 中配置：

```yaml
spring:
  ai:
    dashscope:
      api-key: ${AI_DASHSCOPE_API_KEY}
      chat:
        options:
          model: qwen3-max
```

不要把 API Key 写进代码或提交到仓库。

本地运行前设置环境变量：

```bash
export AI_DASHSCOPE_API_KEY=你的_API_Key
```

## 5. 建议目录结构

在 `src/main/java/edu/softarch/hotelpricingagent/` 下建议创建：

```text
config/
agent/
prompt/
knowledge/
logging/
report/
```

职责：

- `config`：模型和运行配置。
- `agent`：single-agent 主流程。
- `prompt`：system prompt 和 iteration prompt。
- `knowledge`：agent 内置 prior knowledge，建议放在 `src/main/resources/knowledge/`。
- `logging`：conversation log、耗时、token 统计。
- `report`：后续报告素材整理。

## 6. 初始化验证

进入项目目录：

```bash
cd hotel-pricing-agent
mvn spring-boot:run
```

如果能正常启动，说明基础项目可用。

如果暂时没有 API Key，先实现 mock / dry-run 流程，至少验证：

- 能加载 agent 内置 prior knowledge。
- 能生成四轮 ADD 迭代 prompt。
- 能在每轮迭代 prompt 中要求 self-reflection / self-verification。
- 能写出 conversation log。

## 7. 当前阶段完成标准

对照根目录 [../Stage.md](../Stage.md)，本阶段至少应做到：

- Spring Boot 项目可启动。
- Qwen3-Max 配置入口明确。
- agent 主流程结构清楚。
- prompt 和日志逻辑有初始实现。
- 不硬编码任何密钥。
