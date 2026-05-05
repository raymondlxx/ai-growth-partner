'use client'

import { useState } from 'react'
import { ChatInterface } from '@/components/mentor/ChatInterface'
import { ChatMessage } from '@/types'

export default function MentorPage() {
  const [messages, setMessages] = useState<ChatMessage[]>([
    {
      id: '1',
      role: 'assistant',
      content: '你好！我是你的AI成长导师。有什么问题或困惑想和我聊聊吗？我会在这里帮助你。',
      timestamp: new Date().toISOString(),
      emotion: 'neutral',
    },
  ])
  const [isLoading, setIsLoading] = useState(false)

  const handleSend = async (content: string) => {
    const userMessage: ChatMessage = {
      id: Date.now().toString(),
      role: 'user',
      content,
      timestamp: new Date().toISOString(),
    }
    setMessages((prev) => [...prev, userMessage])
    setIsLoading(true)

    try {
      // TODO: Replace with actual API call to AI gateway
      // const res = await apiPost<{ data: ChatMessage }>('/ai/chat', { messages: [...messages, userMessage] })
      // if (res.data.code === 200) setMessages((prev) => [...prev, res.data.data])

      // Simulated AI response for demo
      setTimeout(() => {
        const aiMessage: ChatMessage = {
          id: (Date.now() + 1).toString(),
          role: 'assistant',
          content: '这是一个模拟回复。让我帮你分析一下这个问题...',
          timestamp: new Date().toISOString(),
          emotion: 'neutral',
        }
        setMessages((prev) => [...prev, aiMessage])
        setIsLoading(false)
      }, 1000)
    } catch {
      setIsLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">AI 导师</h1>
        <p className="text-gray-500 text-sm mt-1">您的专属 AI 成长伙伴，随时解答困惑</p>
      </div>
      <ChatInterface messages={messages} onSend={handleSend} isLoading={isLoading} />
    </div>
  )
}