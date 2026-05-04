'use client'

import { Card, CardContent, CardFooter, CardHeader } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { Path } from '@/types'
import { Route, Zap } from 'lucide-react'

interface PathCardProps {
  path: Path
  onSelect?: (pathId: string) => void
}

export function PathCard({ path, onSelect }: PathCardProps) {
  return (
    <Card className="cursor-pointer transition-shadow hover:shadow-md" onClick={() => onSelect?.(path.id)}>
      <CardHeader>
        <div className="flex items-start justify-between">
          <div className="flex items-center gap-2">
            <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-primary/10">
              <Route className="h-5 w-5 text-primary" />
            </div>
            <div>
              <h3 className="font-semibold">{path.name}</h3>
              <p className="text-xs text-muted-foreground">{path.skills.length} 个技能节点</p>
            </div>
          </div>
          <div className="flex items-center gap-1 text-amber-500">
            <Zap className="h-4 w-4" />
            <span className="text-sm font-medium">{path.totalXp} XP</span>
          </div>
        </div>
      </CardHeader>
      <CardContent>
        <p className="text-sm text-muted-foreground">{path.description}</p>
        <div className="mt-4">
          <div className="flex justify-between text-xs text-muted-foreground mb-1">
            <span>学习进度</span>
            <span>{path.progress}%</span>
          </div>
          <div className="h-2 w-full rounded-full bg-secondary">
            <div
              className="h-2 rounded-full bg-primary transition-all"
              style={{ width: `${path.progress}%` }}
            />
          </div>
        </div>
      </CardContent>
      <CardFooter>
        <Button className="w-full" variant={path.progress === 100 ? 'secondary' : 'default'}>
          {path.progress === 100 ? '已完成' : '开始学习'}
        </Button>
      </CardFooter>
    </Card>
  )
}