'use client'

import { useState, useRef, useEffect } from 'react'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { ScrollArea } from '@/components/ui/scroll-area'
import { Badge } from '@/components/ui/badge'
import { ChatMessage } from '@/types'
import { Send, Smile, Sparkles } from 'lucide-react'
import { cn } from '@/lib/utils'
import { formatRelativeTime } from '@/lib/utils'

interface ChatInterfaceProps {
  messages: ChatMessage[]
  onSend: (content: string) => void
  isLoading?: boolean
}

export function ChatInterface({ messages, onSend, isLoading }: ChatInterfaceProps) {
  const [input, setInput] = useState('')
  const scrollRef = useRef<HTMLDivElement>(null)
  const inputRef = useRef<HTMLInputElement>(null)

  useEffect(() => {
    if (scrollRef.current) {
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight
    }
  }, [messages])

  const handleSend = () => {
    if (input.trim() && !isLoading) {
      onSend(input.trim())
      setInput('')
    }
  }

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      handleSend()
    }
  }

  const currentEmotion = messages[messages.length - 1]?.emotion || 'neutral'
  const emotionColors: Record<string, string> = {
    positive: 'bg-green-500/10 text-green-500',
    neutral: 'bg-gray-500/10 text-gray-500',
    negative: 'bg-red-500/10 text-red-500',
  }

  return (
    <div className="flex h-full flex-col rounded-lg border bg-card">
      {/* Header */}
      <div className="flex items-center gap-3 border-b p-4">
        <Avatar className="h-10 w-10">
          <AvatarImage src="/ai-mentor.png" />
          <AvatarFallback className="bg-primary/10">
            <Sparkles className="h-5 w-5 text-primary" />
          </AvatarFallback>
        </Avatar>
        <div className="flex-1">
          <h3 className="font-semibold">AI导师</h3>
          <div className="flex items-center gap-2">
            <Badge className={cn('text-xs', emotionColors[currentEmotion])}>
              <Smile className="h-3 w-3 mr-1" />
              {currentEmotion === 'positive' ? '开心' : currentEmotion === 'negative' ? '担忧' : '平静'}
            </Badge>
          </div>
        </div>
      </div>

      {/* Messages */}
      <ScrollArea ref={scrollRef} className="flex-1 p-4">
        <div className="space-y-4">
          {messages.map((message) => (
            <div
              key={message.id}
              className={cn(
                'flex gap-3',
                message.role === 'user' && 'flex-row-reverse'
              )}
            >
              <Avatar className="h-8 w-8 shrink-0">
                {message.role === 'assistant' ? (
                  <>
                    <AvatarImage src="/ai-mentor.png" />
                    <AvatarFallback className="bg-primary/10">
                      <Sparkles className="h-4 w-4 text-primary" />
                    </AvatarFallback>
                  </>
                ) : (
                  <AvatarFallback>我</AvatarFallback>
                )}
              </Avatar>
              <div
                className={cn(
                  'max-w-[70%] rounded-lg px-3 py-2',
                  message.role === 'user'
                    ? 'bg-primary text-primary-foreground'
                    : 'bg-muted'
                )}
              >
                <p className="text-sm whitespace-pre-wrap">{message.content}</p>
                <p className={cn(
                  'mt-1 text-xs',
                  message.role === 'user' ? 'text-primary-foreground/70' : 'text-muted-foreground'
                )}>
                  {formatRelativeTime(message.timestamp)}
                </p>
              </div>
            </div>
          ))}
          {isLoading && (
            <div className="flex gap-3">
              <Avatar className="h-8 w-8 shrink-0">
                <AvatarFallback className="bg-primary/10">
                  <Sparkles className="h-4 w-4 text-primary" />
                </AvatarFallback>
              </Avatar>
              <div className="max-w-[70%] rounded-lg bg-muted px-3 py-2">
                <div className="flex items-center gap-2">
                  <div className="h-2 w-2 animate-bounce rounded-full bg-muted-foreground/50" style={{ animationDelay: '0ms' }} />
                  <div className="h-2 w-2 animate-bounce rounded-full bg-muted-foreground/50" style={{ animationDelay: '150ms' }} />
                  <div className="h-2 w-2 animate-bounce rounded-full bg-muted-foreground/50" style={{ animationDelay: '300ms' }} />
                </div>
              </div>
            </div>
          )}
        </div>
      </ScrollArea>

      {/* Input */}
      <div className="border-t p-4">
        <div className="flex gap-2">
          <Input
            ref={inputRef}
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="输入消息..."
            disabled={isLoading}
          />
          <Button onClick={handleSend} disabled={!input.trim() || isLoading}>
            <Send className="h-4 w-4" />
          </Button>
        </div>
      </div>
    </div>
  )
}