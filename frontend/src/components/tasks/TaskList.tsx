'use client'

import { TaskCard } from './TaskCard'
import { Task } from '@/types'

interface TaskListProps {
  tasks: Task[]
  onComplete?: (taskId: string) => void
  isCompleting?: boolean
}

export function TaskList({ tasks, onComplete, isCompleting }: TaskListProps) {
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