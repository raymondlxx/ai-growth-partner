'use client'

import { ChatInterface } from '@/components/mentor/ChatInterface'

export default function MentorPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">AI 导师</h1>
        <p className="text-gray-500 text-sm mt-1">您的专属 AI 成长伙伴，随时解答困惑</p>
      </div>
      <ChatInterface />
    </div>
  )
}
