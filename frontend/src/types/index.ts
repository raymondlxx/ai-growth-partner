// API Response wrapper from backend
export interface ApiResponse<T> {
  code: number
  data: T
  message: string
}

// User types
export interface User {
  id: string
  email: string
  name: string
  avatar?: string
  level: number
  xp: number
  title?: string
  bio?: string
  createdAt: string
  updatedAt: string
}

// Task types
export type TaskDifficulty = 'easy' | 'medium' | 'hard' | 'expert'
export type TaskCategory = 'skill' | 'cognitive' | 'emotional' | 'health' | 'career'

export interface Task {
  id: string
  title: string
  description: string
  xpReward: number
  difficulty: TaskDifficulty
  category: TaskCategory
  completed: boolean
  completedAt?: string
  deadline?: string
}

export interface TaskCompletionRequest {
  taskId: string
  proof?: string
}

export interface TaskCompletionResponse {
  xpEarned: number
  newLevel: number
  badgeEarned?: string
}

// Path types
export interface SkillNode {
  id: string
  name: string
  description: string
  status: 'completed' | 'available' | 'locked'
  requiredXp: number
  completedAt?: string
}

export interface Path {
  id: string
  name: string
  description: string
  icon: string
  totalXp: number
  skills: SkillNode[]
  progress: number
}

// Knowledge types
export interface KnowledgeArticle {
  id: string
  title: string
  summary: string
  content: string
  tags: string[]
  author: {
    id: string
    name: string
    avatar?: string
  }
  createdAt: string
  updatedAt: string
  viewCount: number
  likeCount: number
}

export interface KnowledgeSearchParams {
  q?: string
  tags?: string[]
  page?: number
  pageSize?: number
}

// Mentor chat types
export interface ChatMessage {
  id: string
  role: 'user' | 'assistant'
  content: string
  timestamp: string
  emotion?: 'positive' | 'neutral' | 'negative'
}

export interface ChatSession {
  id: string
  messages: ChatMessage[]
  createdAt: string
  updatedAt: string
}

// XP and Level types
export interface XPProgress {
  currentXp: number
  xpToNextLevel: number
  level: number
  totalXp: number
}

// Profile types
export interface UserProfile {
  user: User
  xpProgress: XPProgress
  completedTasksCount: number
  currentPath?: Path
  recentBadges: string[]
}