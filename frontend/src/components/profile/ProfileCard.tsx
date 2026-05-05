'use client'

import { Card, CardContent, CardHeader } from '@/components/ui/card'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Badge } from '@/components/ui/badge'
import { Sparkles, Target, Award } from 'lucide-react'
import { User } from '@/types'
import { formatDate } from '@/lib/utils'

interface ProfileCardProps {
  user: User
  stats?: {
    level: number
    totalXp: number
    xpToNextLevel: number
    tasksCompleted: number
    badgesEarned: number
  } | null
}

export function ProfileCard({ user, stats }: ProfileCardProps) {
  const level = stats?.level ?? user.level ?? 1
  const totalXp = stats?.totalXp ?? user.xp ?? 0
  const xpToNextLevel = stats?.xpToNextLevel ?? 1000
  const tasksCompleted = stats?.tasksCompleted ?? 0
  const badgesEarned = stats?.badgesEarned ?? 0

  const currentXp = totalXp % xpToNextLevel
  const xpPercentage = xpToNextLevel > 0 ? (currentXp / xpToNextLevel) * 100 : 0

  return (
    <div className="space-y-6">
      {/* Basic Info Card */}
      <Card>
        <CardHeader className="flex flex-row items-center gap-4">
          <Avatar className="h-20 w-20">
            <AvatarImage src={user.avatar} />
            <AvatarFallback className="text-2xl">{user.name[0]}</AvatarFallback>
          </Avatar>
          <div className="flex-1">
            <h2 className="text-2xl font-bold">{user.name}</h2>
            <p className="text-muted-foreground">{user.email}</p>
            <div className="mt-2 flex items-center gap-2">
              <Badge variant="secondary">Lv.{level}</Badge>
              {user.title && <Badge>{user.title}</Badge>}
            </div>
          </div>
        </CardHeader>
        <CardContent className="space-y-4">
          {user.bio && (
            <p className="text-sm text-muted-foreground">{user.bio}</p>
          )}
          <div className="grid grid-cols-3 gap-4 text-center">
            <div>
              <div className="flex items-center justify-center gap-1 text-muted-foreground">
                <Target className="h-4 w-4" />
                <span className="text-xs">完成任务</span>
              </div>
              <p className="text-2xl font-bold">{tasksCompleted}</p>
            </div>
            <div>
              <div className="flex items-center justify-center gap-1 text-muted-foreground">
                <Sparkles className="h-4 w-4" />
                <span className="text-xs">总XP</span>
              </div>
              <p className="text-2xl font-bold">{totalXp}</p>
            </div>
            <div>
              <div className="flex items-center justify-center gap-1 text-muted-foreground">
                <Award className="h-4 w-4" />
                <span className="text-xs">徽章</span>
              </div>
              <p className="text-2xl font-bold">{badgesEarned}</p>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* XP Progress Card */}
      <Card>
        <CardHeader>
          <h3 className="font-semibold">等级进度</h3>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="flex items-center justify-between">
            <span className="text-sm text-muted-foreground">Level {level}</span>
            <span className="text-sm text-muted-foreground">Level {level + 1}</span>
          </div>
          <div className="h-3 w-full rounded-full bg-secondary">
            <div 
              className="h-3 rounded-full bg-primary transition-all" 
              style={{ width: `${xpPercentage}%` }} 
            />
          </div>
          <div className="flex justify-between text-sm">
            <span>{currentXp} / {xpToNextLevel} XP</span>
            <span className="text-muted-foreground">还需 {xpToNextLevel - currentXp} XP</span>
          </div>
        </CardContent>
      </Card>

      {/* Member Since */}
      {user.createdAt && (
        <p className="text-center text-xs text-muted-foreground">
          加入于 {formatDate(user.createdAt)}
        </p>
      )}
    </div>
  )
}