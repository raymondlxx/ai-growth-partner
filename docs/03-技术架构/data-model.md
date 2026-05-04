# 核心数据模型

## 领域划分

```
┌─────────────────────────────────────────────────────────────┐
│                      核心数据域                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐       │
│  │ 用户域  │  │ 内容域  │  │ 学习域  │  │ 社交域  │       │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘       │
│                                                             │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐       │
│  │ 交易域  │  │ AI域    │  │ 运营域  │  │  风控域  │       │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 核心实体关系

```
User (用户)
  │
  ├── UserProfile (用户画像)
  │     ├── SkillLevel (技能水平)
  │     ├── LifeEvent (生活事件)
  │     └── EmotionalState (情感状态)
  │
  ├── LearningPath (学习路径)
  │     ├── PathStage (阶段)
  │     └── SkillNode (技能节点)
  │
  ├── Task (任务)
  │     ├── TaskCompletion (任务完成记录)
  │     └── TaskAttempt (任务尝试)
  │
  ├── Badge (徽章)
  │     └── BadgeAward (徽章颁发)
  │
  └── SocialRelation (社交关系)
        ├── Follow (关注)
        ├── Friend (好友)
        └── GroupMember (小组成员)

Content (内容)
  │
  ├── Course (课程)
  │     ├── Chapter (章节)
  │     ├── Lesson (课时)
  │     └── Resource (资源)
  │
  ├── UserContent (用户贡献内容)
  │     └── ContentReview (内容审核)
  │
  └── KnowledgeItem (知识条目)
        └── KnowledgeTag (知识标签)

AI (AI相关)
  │
  ├── Conversation (对话记录)
  │     ├── Message (消息)
  │     └── Intent (意图)
  │
  ├── KnowledgeGraph (知识图谱)
  │     ├── SkillNode (技能节点)
  │     ├── IndustryNode (行业节点)
  │     └── Relation (关系)
  │
  └── Recommendation (推荐记录)
```

## 核心数据模型

### 用户域

```typescript
// 用户基本信息
interface User {
  id: string;
  phone?: string;              // 手机号（可登录）
  email?: string;              // 邮箱（可登录）
  password_hash?: string;     // 密码（第三方登录可为空）
  nickname: string;
  avatar_url?: string;
  status: 'active' | 'inactive' | 'banned';
  created_at: timestamp;
  updated_at: timestamp;
}

// 用户画像
interface UserProfile {
  id: string;
  user_id: string;

  // 基础属性
  age?: number;
  gender?: 'male' | 'female' | 'other';
  education?: 'high_school' | 'college' | 'bachelor' | 'master' | 'doctor';
  occupation?: string;
  industry?: string;
  income_range?: string;

  // 目标与偏好
  goals: Goal[];              // 学习目标
  interests: string[];        // 兴趣标签
  learning_preferences: LearningPreference;

  // 人生阶段
  life_stage: 'student' | 'fresh_grad' | 'employee' | 'manager' |
              'entrepreneur' | 'parent' | 'career_transition';

  // 元数据
  source: 'organic' | 'invite' | 'ads' | 'partner';
  created_at: timestamp;
  updated_at: timestamp;
}

interface Goal {
  type: 'career' | 'skill' | 'income' | 'personal';
  title: string;
  description?: string;
  target_date?: date;
  progress: number;           // 0-100
}

interface LearningPreference {
  preferred_time: string[];   // ['morning', 'night']
  preferred_duration: number; // 分钟
  learning_style: 'visual' | 'auditory' | 'reading' | 'practice';
  difficulty_preference: 'easy' | 'medium' | 'hard';
}

// 技能水平
interface SkillLevel {
  id: string;
  user_id: string;
  skill_id: string;
  level: 'beginner' | 'basic' | 'intermediate' | 'advanced' | 'expert';
  confidence: number;          // 0-1
  evidence: TaskCompletion[];  // 评分证据
  assessed_at: timestamp;
}

// 生活事件
interface LifeEvent {
  id: string;
  user_id: string;
  event_type: 'job_change' | 'promotion' | 'parenting' |
              'relocation' | 'marriage' | 'health_issue' | 'other';
  title: string;
  description?: string;
  impact_on_learning: 'pause' | 'reduce' | 'maintain' | 'accelerate';
  occurred_at: date;
  created_at: timestamp;
}
```

### 学习域

```typescript
// 学习路径
interface LearningPath {
  id: string;
  user_id: string;
  path_type: 'meta_path' | 'custom';  // 元路径 or 自定义
  title: string;
  description: string;

  // 目标技能
  target_skills: string[];
  estimated_duration: number;  // 天

  // 阶段
  stages: PathStage[];

  // 状态
  status: 'active' | 'paused' | 'completed' | 'abandoned';
  progress: number;           // 0-100
  started_at?: timestamp;
  completed_at?: timestamp;

  created_at: timestamp;
  updated_at: timestamp;
}

interface PathStage {
  stage_order: number;
  title: string;
  description: string;
  target_skills: string[];
  estimated_days: number;

  // 阶段内任务
  tasks: Task[];
}

// 任务系统
interface Task {
  id: string;
  path_id?: string;
  stage_id?: string;

  type: 'learning' | 'practice' | 'challenge' | 'quiz' | 'project';
  title: string;
  description: string;

  // 任务配置
  config: {
    content_ids?: string[];   // 关联内容
    passing_score?: number;   // 及格分（测验）
    time_limit?: number;      // 时限（分钟）
    retry_limit?: number;     // 重试次数
    xp_reward: number;        // 经验值奖励
    badge_reward?: string;    // 徽章ID
  };

  // 依赖
  dependencies: string[];      // 前置任务ID

  // 状态
  difficulty: 'easy' | 'medium' | 'hard';
  estimated_minutes: number;

  created_at: timestamp;
}

interface TaskCompletion {
  id: string;
  task_id: string;
  user_id: string;

  // 完成情况
  status: 'in_progress' | 'completed' | 'failed' | 'abandoned';
  started_at: timestamp;
  completed_at?: timestamp;
  time_spent: number;         // 分钟

  // 结果
  score?: number;             // 得分（测验）
  output?: object;            // 产出物
  feedback?: string;          // AI反馈

  // 尝试记录
  attempts: TaskAttempt[];
}

interface TaskAttempt {
  attempt_number: number;
  started_at: timestamp;
  ended_at?: timestamp;
  answers?: object;           // 答题记录
  score?: number;
}
```

### 内容域

```typescript
// 课程
interface Course {
  id: string;
  title: string;
  description: string;
  cover_url?: string;

  // 分类
  category: string;
  tags: string[];
  skills: string[];           // 关联技能

  // 难度
  level: 'beginner' | 'intermediate' | 'advanced';

  // 作者
  author_id: string;
  author_type: 'platform' | 'expert' | 'user';

  // 状态
  status: 'draft' | 'published' | 'archived';
  review_status?: 'pending' | 'approved' | 'rejected';

  // 统计
  enrolled_count: number;
  rating: number;
  avg_rating: number;

  created_at: timestamp;
  updated_at: timestamp;
}

interface Chapter {
  id: string;
  course_id: string;
  order: number;
  title: string;
  description?: string;
}

interface Lesson {
  id: string;
  chapter_id: string;
  order: number;
  title: string;

  type: 'video' | 'text' | 'quiz' | 'exercise' | 'interactive';
  content: {
    url?: string;             // 视频/资源URL
    duration?: number;        // 时长（秒）
    content_text?: string;    // 文本内容
    quiz_config?: object;      // 测验配置
  };

  resources: Resource[];
}

interface Resource {
  id: string;
  lesson_id: string;
  type: 'document' | 'code' | 'image' | 'audio';
  url: string;
  name: string;
}
```

### 社交域

```typescript
// 用户关系
interface SocialRelation {
  id: string;
  user_id: string;
  target_user_id: string;
  type: 'follow' | 'friend' | 'mentor';
  created_at: timestamp;
}

// 学习小组
interface LearningGroup {
  id: string;
  name: string;
  description: string;
  type: 'skill' | 'stage' | 'industry' | 'interest';

  // 成员
  member_count: number;
  max_members?: number;

  // 设置
  is_public: boolean;
  created_by: string;

  created_at: timestamp;
}

interface GroupMember {
  id: string;
  group_id: string;
  user_id: string;
  role: 'owner' | 'admin' | 'member';
  joined_at: timestamp;
}

// 徽章系统
interface Badge {
  id: string;
  code: string;               // 唯一代码
  name: string;
  description: string;
  icon_url: string;

  category: 'achievement' | 'skill' | 'social' | 'milestone';
  rarity: 'common' | 'rare' | 'epic' | 'legendary';

  criteria: object;           // 获得条件
  xp_reward: number;

  created_at: timestamp;
}

interface BadgeAward {
  id: string;
  badge_id: string;
  user_id: string;
  awarded_at: timestamp;
  source: string;              // 触发来源
}
```

### AI域

```typescript
// 对话记录
interface Conversation {
  id: string;
  user_id: string;
  type: 'mentor' | 'review' | 'consultation';

  // AI配置
  ai_config: {
    persona: string;
    style: 'formal' | 'casual' | 'humorous';
  };

  // 统计
  message_count: number;
  avg_rating?: number;

  created_at: timestamp;
  updated_at: timestamp;
}

interface Message {
  id: string;
  conversation_id: string;
  role: 'user' | 'assistant' | 'system';

  content: string;
  content_type: 'text' | 'image' | 'audio';

  // AI分析
  intent?: string;
  sentiment?: 'positive' | 'neutral' | 'negative';

  // 反馈
  rating?: number;
  feedback?: string;

  created_at: timestamp;
}

// 意图识别
interface Intent {
  id: string;
  name: string;
  description: string;
  examples: string[];

  // 响应策略
  response_template?: string;
  required_slots?: string[];
  action?: string;
}

// 知识图谱
interface KGNode {
  id: string;
  type: 'skill' | 'industry' | 'profession' | 'stage';
  name: string;
  name_en?: string;
  description?: string;

  // 属性
  properties: object;

  // 向量（用于语义搜索）
  embedding?: number[];

  created_at: timestamp;
  updated_at: timestamp;
}

interface KGRelation {
  id: string;
  source_id: string;
  target_id: string;
  relation_type: 'contains' | 'requires' | 'combines' |
                 'leads_to' | 'belongs_to' | 'similar_to';

  weight?: number;            // 权重（0-1）
  description?: string;

  created_at: timestamp;
}
```

### 交易域

```typescript
// 订单
interface Order {
  id: string;
  user_id: string;
  order_no: string;

  type: 'course' | 'membership' | 'consultation' | 'content';

  // 金额
  total_amount: number;
  discount_amount: number;
  actual_amount: number;
  currency: 'CNY' | 'USD';

  // 状态
  status: 'pending' | 'paid' | 'cancelled' | 'refunded';

  // 支付
  payment_method?: string;
  paid_at?: timestamp;

  created_at: timestamp;
}

// 会员
interface Membership {
  id: string;
  user_id: string;
  level: 'free' | 'basic' | 'premium' | 'vip';

  // 时间
  started_at: timestamp;
  expires_at?: timestamp;
  auto_renew: boolean;

  // 权益
  permissions: string[];
}
```

## 数据库选型

| 数据类型 | 数据库 | 理由 |
|----------|--------|------|
| 用户、订单等事务数据 | MySQL | ACID、成熟稳定 |
| 画像、缓存 | Redis | 高性能、支持复杂数据结构 |
| 内容、聊天记录 | MongoDB | 灵活schema、非结构化 |
| 行为分析 | ClickHouse | 列式存储、OLAP高效 |
| 知识图谱 | Neo4j / 自研 | 图遍历、关系查询 |
| 向量检索 | Milvus / Qdrant | Embedding相似度搜索 |

---
**创建日期**: 2026-05-04
**版本**: v1.0