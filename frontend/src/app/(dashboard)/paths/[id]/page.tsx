'use client'

import { SkillNode } from '@/components/paths/SkillNode'
import { apiGet } from '@/lib/api'
import { useEffect, useState } from 'react'
import { useParams } from 'next/navigation'

interface SkillNode {
  id: string
  name: string
  description: string
  order: number
  status: 'completed' | 'available' | 'locked'
  requiredXp: number
}

interface CareerPath {
  id: string
  name: string
  description: string
  category: string
  skillNodes: SkillNode[]
}

export default function PathDetailPage() {
  const params = useParams()
  const pathId = params.id as string
  const [path, setPath] = useState<CareerPath | null>(null)

  useEffect(() => {
    const userId = localStorage.getItem('userId') || ''
    apiGet<CareerPath>(`/paths/${pathId}?userId=${userId}`).then(res => {
      if (res.data.code === 200 || res.data.code === 0) setPath(res.data.data)
    }).catch(() => {})
  }, [pathId])

  if (!path) {
    return (
      <div className="flex items-center justify-center py-20">
        <div className="text-gray-500">加载中...</div>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">{path.name}</h1>
        <p className="text-gray-500 text-sm mt-1">{path.description}</p>
      </div>

      <div className="space-y-4">
        {path.skillNodes
          .sort((a, b) => a.order - b.order)
          .map(node => (
            <SkillNode key={node.id} node={node} />
          ))
        }
      </div>
    </div>
  )
}
