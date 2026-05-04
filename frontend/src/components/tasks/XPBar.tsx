'use client'

import { Progress } from '@/components/ui/progress'
import { Sparkles } from 'lucide-react'
import { XPProgress } from '@/types'

interface XPBarProps {
  xpProgress: XPProgress
}

export function XPBar({ xpProgress }: XPBarProps) {
  const progressPercentage = (xpProgress.currentXp / xpProgress.xpToNextLevel) * 100

  return (
    <div className="rounded-lg border bg-card p-4">
      <div className="mb-2 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <Sparkles className="h-5 w-5 text-primary" />
          <span className="font-semibold">Level {xpProgress.level}</span>
        </div>
        <span className="text-sm text-muted-foreground">
          {xpProgress.currentXp} / {xpProgress.xpToNextLevel} XP
        </span>
      </div>
      <Progress value={progressPercentage} className="h-3" />
      <p className="mt-2 text-xs text-muted-foreground">
        再获得 {xpProgress.xpToNextLevel - xpProgress.currentXp} XP 即可升至 Level {xpProgress.level + 1}
      </p>
    </div>
  )
}