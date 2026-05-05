'use client'

import { TaskList } from '@/components/tasks/TaskList'
import { XPBar } from '@/components/tasks/XPBar'
import { apiGet } from '@/lib/api'
import { useEffect, useState } from 'react'
import { useAuth } from '@/contexts/AuthContext'
import type { Task } from '@/types'

interface UserStats {
  level: number
  totalXp: number
  xpToNextLevel: number
  tasksCompleted: number
  badgesEarned: number
}

export default function TasksPage() {
  const { user } = useAuth()
  const [tasks, setTasks] = useState<Task[]>([])
  const [stats, setStats] = useState<UserStats | null>(null)
  const [refreshKey, setRefreshKey] = useState(0)

  useEffect(() => {
    apiGet<{ tasks: Task[] }>('/task/list').then(res => {
      if (res.data.code === 200 || res.data.code === 0) setTasks(res.data.data?.tasks || [])
    }).catch(() => {})
    if (user?.id) {
      apiGet<UserStats>(`/user/stats?userId=${user.id}`).then(res => {
        if (res.data.code === 200 || res.data.code === 0) setStats(res.data.data)
      }).catch(() => {})
    }
  }, [user, refreshKey])

  const handleComplete = () => {
    setRefreshKey(k => k + 1)
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">技能任务</h1>
        <p className="text-gray-500 text-sm mt-1">完成任务获取 XP，提升等级，解锁徽章</p>
      </div>

      {stats && (
        <XPBar xp={stats.totalXp} level={stats.level} xpToNext={stats.xpToNextLevel} />
      )}

      <TaskList tasks={tasks} onComplete={handleComplete} />
    </div>
  )
}