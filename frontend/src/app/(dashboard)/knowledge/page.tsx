'use client'

import { KnowledgeSearch } from '@/components/knowledge/KnowledgeSearch'

export default function KnowledgePage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">成长智库</h1>
        <p className="text-gray-500 text-sm mt-1">AI 驱动的知识库，搜索您关心的成长话题</p>
      </div>
      <KnowledgeSearch />
    </div>
  )
}
