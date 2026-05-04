'use client'

import { useState } from 'react'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { cn } from '@/lib/utils'
import { NavItem } from './NavItem'
import { Button } from '@/components/ui/button'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { ScrollArea } from '@/components/ui/scroll-area'
import {
  Home,
  User,
  ListTodo,
  Route,
  BookOpen,
  MessageCircle,
  ChevronLeft,
  ChevronRight,
  Sparkles,
} from 'lucide-react'
import { Progress } from '@/components/ui/progress'

interface SidebarProps {
  user?: {
    name: string
    avatar?: string
    level: number
    xp: number
  }
}

const navItems = [
  { href: '/', icon: Home, label: '首页' },
  { href: '/profile', icon: User, label: '我的画像' },
  { href: '/tasks', icon: ListTodo, label: '技能任务' },
  { href: '/paths', icon: Route, label: '成长路径' },
  { href: '/knowledge', icon: BookOpen, label: '知识智库' },
  { href: '/mentor', icon: MessageCircle, label: 'AI导师' },
]

export function Sidebar({ user }: SidebarProps) {
  const [collapsed, setCollapsed] = useState(false)
  const pathname = usePathname()

  const xpProgress = user ? (user.xp % 100) : 0
  const xpToNextLevel = 100

  return (
    <aside
      className={cn(
        'relative flex flex-col border-r bg-card transition-all duration-300',
        collapsed ? 'w-16' : 'w-64'
      )}
    >
      {/* Logo */}
      <div className="flex h-14 items-center border-b px-4">
        <Link href="/" className="flex items-center gap-2">
          <Sparkles className="h-6 w-6 text-primary" />
          {!collapsed && (
            <span className="font-semibold">AI成长伴侣</span>
          )}
        </Link>
      </div>

      {/* User Info */}
      {user && (
        <div className={cn('border-b p-4', collapsed && 'flex justify-center')}>
          <div className={cn('flex items-center gap-3', collapsed && 'flex-col')}>
            <Avatar className="h-10 w-10">
              <AvatarImage src={user.avatar} />
              <AvatarFallback>{user.name[0]}</AvatarFallback>
            </Avatar>
            {!collapsed && (
              <div className="flex-1 overflow-hidden">
                <p className="truncate text-sm font-medium">{user.name}</p>
                <div className="mt-1">
                  <div className="flex items-center justify-between text-xs text-muted-foreground">
                    <span>Lv.{user.level}</span>
                    <span>{xpProgress}/{xpToNextLevel} XP</span>
                  </div>
                  <Progress value={(xpProgress / xpToNextLevel) * 100} className="mt-1 h-1.5" />
                </div>
              </div>
            )}
          </div>
        </div>
      )}

      {/* Navigation */}
      <ScrollArea className="flex-1 py-4">
        <nav className="space-y-1 px-2">
          {navItems.map((item) => (
            <NavItem
              key={item.href}
              href={item.href}
              icon={item.icon}
              label={item.label}
              collapsed={collapsed}
              isActive={
                item.href === '/'
                  ? pathname === '/'
                  : pathname.startsWith(item.href)
              }
            />
          ))}
        </nav>
      </ScrollArea>

      {/* Collapse Button */}
      <Button
        variant="ghost"
        size="icon"
        className="absolute -right-3 top-20 z-10 h-6 w-6 rounded-full border bg-background shadow-sm"
        onClick={() => setCollapsed(!collapsed)}
      >
        {collapsed ? (
          <ChevronRight className="h-3 w-3" />
        ) : (
          <ChevronLeft className="h-3 w-3" />
        )}
      </Button>
    </aside>
  )
}