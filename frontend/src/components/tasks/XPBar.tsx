'use client'

import { Progress } from '@/components/ui/progress'
import { Sparkles } from 'lucide-react'

interface XPBarProps {
  xp: number
  level: number
  xpToNext: number
}

export function XPBar({ xp, level, xpToNext }: XPBarProps) {
  const currentXp = xp % xpToNext
  const progressPercentage = xpToNext > 0 ? (currentXp / xpToNext) * 100 : 0

  return (
    <div className="rounded-lg border bg-card p-4">
      <div className="mb-2 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <Sparkles className="h-5 w-5 text-primary" />
          <span className="font-semibold">Level {level}</span>
        </div>
        <span className="text-sm text-muted-foreground">
          {currentXp} / {xpToNext} XP
        </span>
      </div>
      <Progress value={progressPercentage} className="h-3" />
      <p className="mt-2 text-xs text-muted-foreground">
        再获得 {xpToNext - currentXp} XP 即可升至 Level {level + 1}
      </p>
    </div>
  )
}