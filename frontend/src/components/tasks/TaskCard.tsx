'use client'

import { Card, CardContent, CardFooter, CardHeader } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Task } from '@/types'
import { Zap, Calendar } from 'lucide-react'
import { getDifficultyBgColor } from '@/lib/utils'

interface TaskCardProps {
  task: Task
  onComplete?: (taskId: string) => void
  isCompleting?: boolean
}

export function TaskCard({ task, onComplete, isCompleting }: TaskCardProps) {
  return (
    <Card className="flex flex-col">
      <CardHeader>
        <div className="flex items-start justify-between">
          <div className="flex-1">
            <h3 className="font-semibold leading-tight">{task.title}</h3>
            <Badge className={`mt-2 ${getDifficultyBgColor(task.difficulty)}`}>
              {task.difficulty === 'easy' ? '简单' : task.difficulty === 'medium' ? '中等' : task.difficulty === 'hard' ? '困难' : '专家'}
            </Badge>
          </div>
          <div className="flex items-center gap-1 text-amber-500">
            <Zap className="h-4 w-4" />
            <span className="text-sm font-medium">{task.xpReward} XP</span>
          </div>
        </div>
      </CardHeader>
      <CardContent className="flex-1">
        <p className="text-sm text-muted-foreground line-clamp-3">{task.description}</p>
        <div className="mt-3 flex items-center gap-2 text-xs text-muted-foreground">
          <Badge variant="secondary" className="text-xs">
            {task.category === 'skill' ? '技能' : task.category === 'cognitive' ? '认知' : task.category === 'emotional' ? '情绪' : task.category === 'health' ? '健康' : '职业'}
          </Badge>
          {task.deadline && (
            <span className="flex items-center gap-1">
              <Calendar className="h-3 w-3" />
              {new Date(task.deadline).toLocaleDateString('zh-CN')}
            </span>
          )}
        </div>
      </CardContent>
      <CardFooter>
        {task.completed ? (
          <Button variant="secondary" className="w-full" disabled>
            已完成 ✓
          </Button>
        ) : (
          <Button
            className="w-full"
            onClick={() => onComplete?.(task.id)}
            disabled={isCompleting}
          >
            {isCompleting ? '提交中...' : '完成任务'}
          </Button>
        )}
      </CardFooter>
    </Card>
  )
}