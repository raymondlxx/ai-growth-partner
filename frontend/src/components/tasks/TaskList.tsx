'use client'

import { TaskCard } from './TaskCard'
import { Task } from '@/types'

interface TaskListProps {
  tasks: Task[]
  onComplete?: (taskId: string) => void
  isCompleting?: boolean
}

export function TaskList({ tasks, onComplete, isCompleting }: TaskListProps) {
  if (tasks.length === 0) {
    return (
      <div className="text-center py-12 text-muted-foreground">
        暂无任务，去探索更多内容吧！
      </div>
    )
  }

  return (
    <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {tasks.map((task) => (
        <TaskCard
          key={task.id}
          task={task}
          onComplete={onComplete}
          isCompleting={isCompleting}
        />
      ))}
    </div>
  )
}