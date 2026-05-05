'use client'

import { PathCard } from '@/components/paths/PathCard'
import { apiGet } from '@/lib/api'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import type { Path } from '@/types'

interface CareerPath {
  id: string
  name: string
  description: string
  category: string
  totalSkills: number
  completedSkills: number
  skillNodes: {
    id: string
    name: string
    description: string
    order: number
    isCompleted: boolean
    isUnlocked: boolean
  }[]
}

function mapToPath(careerPath: CareerPath): Path {
  return {
    id: careerPath.id,
    name: careerPath.name,
    description: careerPath.description,
    icon: 'Route',
    totalXp: careerPath.totalSkills * 100,
    skills: careerPath.skillNodes.map(node => ({
      id: node.id,
      name: node.name,
      description: node.description,
      status: node.isCompleted ? 'completed' : node.isUnlocked ? 'available' : 'locked',
      requiredXp: node.order * 50,
    })),
    progress: careerPath.totalSkills > 0 
      ? Math.round((careerPath.completedSkills / careerPath.totalSkills) * 100) 
      : 0,
  }
}

export default function PathsPage() {
  const [paths, setPaths] = useState<Path[]>([])
  const router = useRouter()

  useEffect(() => {
    apiGet<CareerPath[]>('/paths').then(res => {
      if (res.data.code === 200 || res.data.code === 0) {
        setPaths((res.data.data || []).map(mapToPath))
      }
    }).catch(() => {})
  }, [])

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">成长路径</h1>
        <p className="text-gray-500 text-sm mt-1">选择您的职业方向，AI 规划最优技能习得路径</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {paths.map(path => (
          <PathCard
            key={path.id}
            path={path}
            onSelect={(id) => router.push(`/paths/${id}`)}
          />
        ))}
      </div>

      {paths.length === 0 && (
        <div className="text-center py-12 text-gray-500">
          <p>暂无路径数据</p>
        </div>
      )}
    </div>
  )
}