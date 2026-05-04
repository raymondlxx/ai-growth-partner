'use client'

import { SkillNode as SkillNodeType } from '@/types'
import { CheckCircle, Circle, Lock } from 'lucide-react'
import { cn } from '@/lib/utils'

interface SkillNodeProps {
  node: SkillNodeType
  isActive?: boolean
  onClick?: () => void
}

export function SkillNode({ node, isActive, onClick }: SkillNodeProps) {
  const Icon = node.status === 'completed' ? CheckCircle : node.status === 'available' ? Circle : Lock
  
  return (
    <button
      onClick={onClick}
      disabled={node.status === 'locked'}
      className={cn(
        'flex flex-col items-center rounded-lg border p-4 transition-all text-left w-full',
        node.status === 'completed' && 'border-green-500 bg-green-500/5',
        node.status === 'available' && 'border-primary bg-primary/5 hover:border-primary',
        node.status === 'locked' && 'border-muted bg-muted/50 cursor-not-allowed',
        isActive && 'ring-2 ring-primary'
      )}
    >
      <Icon
        className={cn(
          'h-6 w-6 mb-2',
          node.status === 'completed' && 'text-green-500',
          node.status === 'available' && 'text-primary',
          node.status === 'locked' && 'text-muted-foreground'
        )}
      />
      <h4 className={cn(
        'font-medium text-sm',
        node.status === 'locked' && 'text-muted-foreground'
      )}>
        {node.name}
      </h4>
      <p className="mt-1 text-xs text-muted-foreground line-clamp-2">
        {node.description}
      </p>
      {node.status === 'available' && (
        <Badge variant="secondary" className="mt-2 text-xs">
          {node.requiredXp} XP
        </Badge>
      )}
    </button>
  )
}

// Need Badge for SkillNode
import { Badge } from '@/components/ui/badge'