'use client'

import { Card, CardContent, CardHeader } from '@/components/ui/card'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Badge } from '@/components/ui/badge'
import { Progress } from '@/components/ui/progress'
import { Sparkles, Target, Award } from 'lucide-react'
import { User, XPProgress } from '@/types'
import { formatDate } from '@/lib/utils'

interface ProfileCardProps {
  user: User
  xpProgress: XPProgress
  completedTasksCount: number
  recentBadges: string[]
}

export function ProfileCard({ user, xpProgress, completedTasksCount, recentBadges }: ProfileCardProps) {
  const xpPercentage = (xpProgress.currentXp / xpProgress.xpToNextLevel) * 100

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
              <Badge variant="secondary">Lv.{xpProgress.level}</Badge>
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
              <p className="text-2xl font-bold">{completedTasksCount}</p>
            </div>
            <div>
              <div className="flex items-center justify-center gap-1 text-muted-foreground">
                <Sparkles className="h-4 w-4" />
                <span className="text-xs">总XP</span>
              </div>
              <p className="text-2xl font-bold">{xpProgress.totalXp}</p>
            </div>
            <div>
              <div className="flex items-center justify-center gap-1 text-muted-foreground">
                <Award className="h-4 w-4" />
                <span className="text-xs">徽章</span>
              </div>
              <p className="text-2xl font-bold">{recentBadges.length}</p>
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
            <span className="text-sm text-muted-foreground">Level {xpProgress.level}</span>
            <span className="text-sm text-muted-foreground">Level {xpProgress.level + 1}</span>
          </div>
          <Progress value={xpPercentage} className="h-3" />
          <div className="flex justify-between text-sm">
            <span>{xpProgress.currentXp} / {xpProgress.xpToNextLevel} XP</span>
            <span className="text-muted-foreground">还需 {xpProgress.xpToNextLevel - xpProgress.currentXp} XP</span>
          </div>
        </CardContent>
      </Card>

      {/* Recent Badges Card */}
      {recentBadges.length > 0 && (
        <Card>
          <CardHeader>
            <h3 className="font-semibold">最近徽章</h3>
          </CardHeader>
          <CardContent>
            <div className="flex flex-wrap gap-2">
              {recentBadges.map((badge, index) => (
                <Badge key={index} variant="outline">{badge}</Badge>
              ))}
            </div>
          </CardContent>
        </Card>
      )}

      {/* Member Since */}
      <p className="text-center text-xs text-muted-foreground">
        加入于 {formatDate(user.createdAt)}
      </p>
    </div>
  )
}