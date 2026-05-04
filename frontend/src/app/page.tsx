import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { Card, CardContent } from '@/components/ui/card'
import { Sparkles, MessageCircle, Route, ListTodo, BookOpen, Zap } from 'lucide-react'

export default function HomePage() {
  const features = [
    {
      icon: MessageCircle,
      title: 'AI导师',
      description: '智能对话指导，随时随地解答你的成长困惑',
    },
    {
      icon: Route,
      title: '路径规划',
      description: 'AI驱动的技能到职业路径引擎',
    },
    {
      icon: ListTodo,
      title: '任务系统',
      description: '游戏化的XP、等级、徽章任务系统',
    },
    {
      icon: BookOpen,
      title: '知识库',
      description: '个人知识沉淀与内容共创平台',
    },
  ]

  return (
    <div className="min-h-screen bg-gradient-to-b from-background to-secondary/20">
      {/* Header */}
      <header className="border-b">
        <div className="container mx-auto flex h-16 items-center justify-between px-4">
          <div className="flex items-center gap-2">
            <Sparkles className="h-6 w-6 text-primary" />
            <span className="font-semibold">AI成长伴侣</span>
          </div>
          <nav className="flex items-center gap-4">
            <Link href="/login">
              <Button variant="ghost">登录</Button>
            </Link>
            <Link href="/register">
              <Button>开始使用</Button>
            </Link>
          </nav>
        </div>
      </header>

      {/* Hero Section */}
      <section className="container mx-auto px-4 py-24 text-center">
        <div className="mx-auto max-w-3xl">
          <h1 className="text-4xl font-bold tracking-tight sm:text-5xl md:text-6xl">
            你的<span className="text-primary">AI驱动</span>成长伙伴
          </h1>
          <p className="mt-6 text-lg text-muted-foreground">
            在职业、认知、情绪和健康维度实现自我迭代，AI导师陪伴你的每一步成长
          </p>
          <div className="mt-10 flex items-center justify-center gap-4">
            <Link href="/register">
              <Button size="lg" className="gap-2">
                <Zap className="h-4 w-4" />
                立即开始
              </Button>
            </Link>
            <Link href="/login">
              <Button size="lg" variant="outline">
                登录
              </Button>
            </Link>
          </div>
        </div>

        {/* Stats */}
        <div className="mt-20 grid grid-cols-2 gap-8 md:grid-cols-4">
          <div>
            <p className="text-3xl font-bold text-primary">10K+</p>
            <p className="text-sm text-muted-foreground">活跃用户</p>
          </div>
          <div>
            <p className="text-3xl font-bold text-primary">50K+</p>
            <p className="text-sm text-muted-foreground">完成任务</p>
          </div>
          <div>
            <p className="text-3xl font-bold text-primary">95%</p>
            <p className="text-sm text-muted-foreground">用户满意度</p>
          </div>
          <div>
            <p className="text-3xl font-bold text-primary">24/7</p>
            <p className="text-sm text-muted-foreground">AI导师在线</p>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="container mx-auto px-4 py-16">
        <div className="text-center">
          <h2 className="text-3xl font-bold">核心功能</h2>
          <p className="mt-2 text-muted-foreground">全方位支持你的成长之旅</p>
        </div>
        <div className="mt-12 grid gap-6 md:grid-cols-2 lg:grid-cols-4">
          {features.map((feature) => (
            <Card key={feature.title} className="text-center">
              <CardContent className="pt-6">
                <div className="mx-auto flex h-12 w-12 items-center justify-center rounded-lg bg-primary/10">
                  <feature.icon className="h-6 w-6 text-primary" />
                </div>
                <h3 className="mt-4 font-semibold">{feature.title}</h3>
                <p className="mt-2 text-sm text-muted-foreground">
                  {feature.description}
                </p>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      {/* CTA Section */}
      <section className="container mx-auto px-4 py-16">
        <div className="rounded-2xl bg-primary/10 p-8 text-center">
          <h2 className="text-2xl font-bold">准备好开始你的成长之旅了吗？</h2>
          <p className="mt-2 text-muted-foreground">
            加入我们，让AI成为你成长路上的贴心伙伴
          </p>
          <Link href="/register" className="mt-6 inline-block">
            <Button size="lg" className="gap-2">
              <Sparkles className="h-4 w-4" />
              免费开始
            </Button>
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t py-8">
        <div className="container mx-auto px-4 text-center text-sm text-muted-foreground">
          <p>© 2024 AI人生成长伴侣. 保留所有权利.</p>
        </div>
      </footer>
    </div>
  )
}