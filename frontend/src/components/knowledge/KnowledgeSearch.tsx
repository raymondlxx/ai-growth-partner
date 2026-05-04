'use client'

import { useState } from 'react'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { Search, Eye, Heart } from 'lucide-react'
import { KnowledgeArticle } from '@/types'
import { formatRelativeTime } from '@/lib/utils'

interface KnowledgeSearchProps {
  results: KnowledgeArticle[]
  isLoading: boolean
  onSearch: (query: string) => void
}

export function KnowledgeSearch({ results, isLoading, onSearch }: KnowledgeSearchProps) {
  const [query, setQuery] = useState('')

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (query.trim()) {
      onSearch(query.trim())
    }
  }

  return (
    <div className="space-y-6">
      {/* Search Form */}
      <form onSubmit={handleSubmit} className="flex gap-2">
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
          <Input
            placeholder="搜索知识库..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className="pl-10"
          />
        </div>
        <Button type="submit" disabled={isLoading}>
          {isLoading ? '搜索中...' : '搜索'}
        </Button>
      </form>

      {/* Search Results */}
      {results.length > 0 ? (
        <div className="space-y-4">
          {results.map((article) => (
            <Card key={article.id} className="transition-shadow hover:shadow-md">
              <CardHeader className="pb-2">
                <div className="flex items-start justify-between">
                  <CardTitle className="text-lg">{article.title}</CardTitle>
                  <Badge variant="secondary">{article.tags[0]}</Badge>
                </div>
                <p className="text-sm text-muted-foreground">{article.author.name}</p>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground mb-3">{article.summary}</p>
                <div className="flex items-center gap-4 text-xs text-muted-foreground">
                  <span className="flex items-center gap-1">
                    <Eye className="h-3 w-3" />
                    {article.viewCount}
                  </span>
                  <span className="flex items-center gap-1">
                    <Heart className="h-3 w-3" />
                    {article.likeCount}
                  </span>
                  <span>{formatRelativeTime(article.createdAt)}</span>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      ) : (
        <div className="flex flex-col items-center justify-center py-12 text-center">
          <Search className="h-12 w-12 text-muted-foreground/50 mb-4" />
          <h3 className="font-semibold">搜索知识库</h3>
          <p className="text-sm text-muted-foreground mt-1">
            输入关键词开始搜索文章
          </p>
        </div>
      )}
    </div>
  )
}