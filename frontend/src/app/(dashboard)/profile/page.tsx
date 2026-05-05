'use client'

import { ProfileCard } from '@/components/profile/ProfileCard'
import { XPBar } from '@/components/tasks/XPBar'
import { apiGet } from '@/lib/api'
import { useEffect, useState } from 'react'
import { useAuth } from '@/contexts/AuthContext'
import type { User, XPProgress } from '@/types'

interface UserStats {
  level: number
  totalXp: number
  tasksCompleted: number
  badgesEarned: number
  xpToNextLevel: number
}

export default function ProfilePage() {
  const { user } = useAuth()
  const [stats, setStats] = useState<UserStats | null>(null)

  useEffect(() => {
    if (user?.id) {
      apiGet<UserStats>(`/user/stats?userId=${user.id}`).then(res => {
        if (res.data.code === 200 || res.data.code === 0) setStats(res.data.data)
      }).catch(() => {})
    }
  }, [user])

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">我的画像</h1>
        <p className="text-gray-500 text-sm mt-1">查看您的成长数据和个人信息</p>
      </div>

      {stats && (
        <XPBar xp={stats.totalXp} level={stats.level} xpToNext={stats.xpToNextLevel} />
      )}

      {user && <ProfileCard user={user} stats={stats} />}
    </div>
  )
}