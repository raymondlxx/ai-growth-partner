# MVP 执行计划 (2026-05-05 起)

## 当前项目状态

### 已完成 ✅
| 模块 | 状态 | 说明 |
|------|------|------|
| 用户注册/登录 | ✅ | JWT + Redis token，修复 key 不一致 bug |
| postcss.config.js | ✅ | Tailwind CSS 编译正常 |
| nginx 反向代理 | ✅ | 8080-8084 路由正确 |
| 数据库初始化 | ✅ | users 表已创建 |
| 微服务架构 | ✅ | 5 个服务全部在线 |

### 服务健康状态
```
user-service:     8080 ✅  (需要 token)
task-service:     8081 ✅  (返回 200)
path-service:     8082 ✅  (返回 200)
content-service:  8083 ✅  (返回 200)
ai-gateway:       8084 ✅  (GET 405 正常)
frontend:         3000 ✅  (Next.js)
```

---

## MVP 待开发功能 (按优先级)

### P0 — 核心流程必须通

#### 1. 登录状态保持 (Session/Cookie)
- **问题**: 目前 token 只存 Redis，刷新页面后丢失
- **方案**: 前端存 token 到 localStorage，axios 拦截器注入 `Authorization: Bearer`
- **文件**: `frontend/src/lib/api.ts`, `AuthForms.tsx`

#### 2. 任务系统数据初始化
- **问题**: 文档规划了任务/XP/徽章，但表可能有但缺初始化数据
- **方案**: 检查 `task-service` 数据初始化配置，确认种子数据存在
- **文件**: `DataInitConfig.java`

#### 3. 路径规划数据初始化
- **问题**: `path-service` 需要预设 3-5 个职业路径模板
- **方案**: 检查并补充 CareerPath 种子数据
- **文件**: `DataInitConfig.java`, `CareerPathRepository.java`

#### 4. AI 导师对话能力
- **问题**: `ai-gateway` 的 `/api/ai/chat` 目前是 mock 响应
- **方案**: 接入 OpenAI API (LangChain4j)，补充 PromptTemplateService
- **环境变量**: `OPENAI_API_KEY`
- **文件**: `application.yml`, `LangChain4jConfig.java`, `MockAiService.java`

#### 5. 前端页面与后端接口对接
- **问题**: 前端页面大多为空壳，未调用真实 API
- **需要对接**:
  - `/api/tasks` → 任务列表
  - `/api/paths` → 路径列表
  - `/api/knowledge` → 知识库
  - `/api/ai/chat` → AI 对话

### P1 — 体验完善

#### 6. 启动脚本 `start-all.sh`
- 一键启动所有 5 个后端服务
- 检查 MySQL/Redis/MongoDB 健康状态
- 前端 `next dev` 独立启动

#### 7. 用户画像 (初始问卷)
- 用户注册后引导完成初始问卷
- 收集：目标职业、技能水平、学习时间、兴趣方向
- 生成初始用户画像

#### 8. 首页仪表盘
- 今日任务、当前路径进度、XP 进度条
- AI 导师快捷入口

### P2 — 增长功能

#### 9. 知识库内容初始化
- 预置 10-20 篇高质量学习内容
- 分类：Python 入门、职场沟通、产品思维
- 支持 RAG 检索

#### 10. 每日任务推送
- 基于用户画像 + 遗忘曲线生成每日任务
- 定时任务 (Redis sorted set)

---

## 执行计划 (Week 1-4)

```
Week 1: 前后端连通 + 数据初始化
────────────────────────────────
[x]  day1  后端所有服务本地启动 ✅ (之前已完成)
[x]  day1  前端 postcss + Tailwind ✅ (之前已完成)
[x]  day1  用户注册/登录全流程 ✅ (之前已完成)
[ ]  day2  前端 API 客户端 (axios + interceptors)
[ ]  day2  登录状态保持 (localStorage → header 注入)
[ ]  day2  task-service 数据初始化检查
[ ]  day2  path-service 职业路径种子数据
[ ]  day3  content-service 知识库种子数据
[ ]  day3  前端任务列表 + 任务卡片
[ ]  day3  前端路径列表 + 路径卡片
[ ]  day3  启动脚本 start-all.sh

Week 2: AI 导师 + 核心交互
────────────────────────────────
[ ]  day4  配置 OPENAI_API_KEY
[ ]  day4  ai-gateway 接入 LangChain4j + OpenAI
[ ]  day4  PromptTemplateService 提示词模板
[ ]  day5  AI 对话接口 /api/ai/chat 真实调用
[ ]  day5  前端 ChatInterface 对接后端
[ ]  day5  AI 情感识别 (EmotionEngine) 接入
[ ]  day6  前端 Mentor 页面完整交互
[ ]  day6  用户画像初始问卷 (注册后引导)

Week 3: 游戏化 + 数据流转
────────────────────────────────
[ ]  day7   XP/等级/徽章系统检查
[ ]  day7   任务完成流程 (前端提交 → 后端评估 → 奖励发放)
[ ]  day8   每日任务生成逻辑
[ ]  day8   前端 XPBar 组件 + 等级显示
[ ]  day8   首页仪表盘 (任务/路径/XP)
[ ]  day9   路径详情页 (技能节点树)
[ ]  day9   用户 Profile 页面

Week 4: 验证 + 文档 + 灰度
────────────────────────────────
[ ]  day10  end-to-end 全流程测试
[ ]  day10  更新 MVP 技术方案文档
[ ]  day11  README 更新 (快速启动指南)
[ ]  day11  API 文档 (Swagger/OpenAPI)
[ ]  day12  灰度测试准备
[ ]  day12  Git commit 所有完成项
```

---

## 技术债务登记

| # | 问题 | 影响 | 建议 |
|---|------|------|------|
| T1 | `common` 模块两个 `ApiResponse.java` | 二义性 | 合并为一个，删除重复 |
| T2 | social-service 只有 Application 类 | 骨架未完成 | MVP 阶段跳过 |
| T3 | `schema.sql` 在 user-service 但从未执行 | 手动建表 | 改用 JPA `spring.jpa.hibernate.ddl-auto=update` |
| T4 | `task-service` Hibernate JTA warning | 启动警告 | 配置 JTA platform 或移除 JTA 依赖 |
| T5 | 前端 `AuthForms.tsx` 只有一个祖传组件 | 维护性差 | 拆分为 LoginForm / RegisterForm |

---

## 快速启动命令

```bash
# 1. 基础设施 (Docker)
cd ~/works/ai-growth-partner/infra
docker-compose up -d

# 2. 后端服务 (每个终端一个)
cd ~/works/ai-growth-partner/backend
java -jar user-service/target/user-service-1.0.0-SNAPSHOT.jar   # 8080
java -jar task-service/target/task-service-1.0.0-SNAPSHOT.jar   # 8081
java -jar path-service/target/path-service-1.0.0-SNAPSHOT.jar   # 8082
java -jar content-service/target/content-service-1.0.0-SNAPSHOT.jar  # 8083
java -jar ai-gateway/target/ai-gateway-1.0.0-SNAPSHOT.jar       # 8084

# 3. 前端
cd ~/works/ai-growth-partner/frontend
npm run dev

# 4. 打开浏览器
open http://localhost:3000
```

---

**创建日期**: 2026-05-05
**版本**: v1.0
**负责人**: AI Growth Partner Team
