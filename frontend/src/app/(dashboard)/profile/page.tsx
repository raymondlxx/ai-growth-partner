'use client'

import { ProfileCard } from '@/components/profile/ProfileCard'
import { XPBar } from '@/components/tasks/XPBar'
import { apiGet } from '@/lib/api'
import { useEffect, useState } from 'react'

interface UserStats {
  level: number
  totalXp: number
  tasksCompleted: number
  badgesEarned: number
  xpToNextLevel: number
}

interface UserProfile {
  userId: string
  nickname: string
  email: string
  careerGoal: string
  skills: string[]
  joinedAt: string
}

export default function ProfilePage() {
  const [profile, setProfile] = useState<UserProfile | null>(null)
  const [stats, setStats] = useState<UserStats | null>(null)

  useEffect(() => {
    const userId = localStorage.getItem('userId') || ''
    apiGet<UserProfile>('/user/profile').then(res => {
      if (res.data.code === 200 || res.data.code === 0) setProfile(res.data.data)
    }).catch(() => {})
    apiGet<UserStats>(`/user/stats?userId=${userId}`).then(res => {
      if (res.data.code === 200 || res.data.code === 0) setStats(res.data.data)
    }).catch(() => {})
  }, [])

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">我的画像</h1>
        <p className="text-gray-500 text-sm mt-1">查看您的成长数据和个人信息</p>
      </div>

      {stats && <XPBar xp={stats.totalXp} level={stats.level} xpToNext={stats.xpToNextLevel} />}

      {profile && <ProfileCard profile={profile} stats={stats} />}
    </div>
  )
}
