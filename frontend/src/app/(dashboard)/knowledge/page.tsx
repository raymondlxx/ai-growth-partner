'use client'

import { useState } from 'react'
import { KnowledgeSearch } from '@/components/knowledge/KnowledgeSearch'
import { KnowledgeArticle } from '@/types'

export default function KnowledgePage() {
  const [results, setResults] = useState<KnowledgeArticle[]>([])
  const [isLoading, setIsLoading] = useState(false)

  const handleSearch = async (query: string) => {
    setIsLoading(true)
    try {
      // TODO: Replace with actual API call
      // const res = await apiGet<KnowledgeArticle[]>(`/knowledge/search?q=${query}`)
      // if (res.data.code === 200) setResults(res.data.data)
      setResults([])
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">成长智库</h1>
        <p className="text-gray-500 text-sm mt-1">AI 驱动的知识库，搜索您关心的成长话题</p>
      </div>
      <KnowledgeSearch results={results} isLoading={isLoading} onSearch={handleSearch} />
    </div>
  )
}