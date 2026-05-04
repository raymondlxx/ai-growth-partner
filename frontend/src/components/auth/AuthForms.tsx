'use client'

import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'

const loginSchema = z.object({
  email: z.string().email('请输入有效的邮箱地址'),
  password: z.string().min(6, '密码至少6个字符'),
})

const registerSchema = z.object({
  name: z.string().min(2, '姓名至少2个字符'),
  email: z.string().email('请输入有效的邮箱地址'),
  password: z.string().min(6, '密码至少6个字符'),
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: '两次密码输入不一致',
  path: ['confirmPassword'],
})

type LoginFormData = z.infer<typeof loginSchema>
type RegisterFormData = z.infer<typeof registerSchema>

interface AuthFormsProps {
  onLogin?: (data: LoginFormData) => Promise<{ success: boolean; message: string }>
  onRegister?: (data: RegisterFormData) => Promise<{ success: boolean; message: string }>
  isLoading?: boolean
}

export function AuthForms({ onLogin, onRegister, isLoading }: AuthFormsProps) {
  const [activeTab, setActiveTab] = useState<'login' | 'register'>('login')
  const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null)

  const loginForm = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
    defaultValues: { email: '', password: '' },
  })

  const registerForm = useForm<RegisterFormData>({
    resolver: zodResolver(registerSchema),
    defaultValues: { name: '', email: '', password: '', confirmPassword: '' },
  })

  const handleLogin = async (data: LoginFormData) => {
    if (!onLogin) return
    setMessage(null)
    const result = await onLogin(data)
    if (!result.success) {
      setMessage({ type: 'error', text: result.message })
    }
  }

  const handleRegister = async (data: RegisterFormData) => {
    if (!onRegister) return
    setMessage(null)
    const result = await onRegister(data)
    if (!result.success) {
      setMessage({ type: 'error', text: result.message })
    }
  }

  return (
    <Card className="w-full max-w-md">
      <Tabs value={activeTab} onValueChange={(v) => setActiveTab(v as typeof activeTab)}>
        <CardHeader>
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="login">登录</TabsTrigger>
            <TabsTrigger value="register">注册</TabsTrigger>
          </TabsList>
        </CardHeader>

        <TabsContent value="login">
          <form onSubmit={loginForm.handleSubmit(handleLogin)}>
            <CardContent className="space-y-4">
              <div className="space-y-2">
                <label className="text-sm font-medium">邮箱</label>
                <Input
                  type="email"
                  placeholder="your@email.com"
                  {...loginForm.register('email')}
                />
                {loginForm.formState.errors.email && (
                  <p className="text-xs text-red-500">{loginForm.formState.errors.email.message}</p>
                )}
              </div>
              <div className="space-y-2">
                <label className="text-sm font-medium">密码</label>
                <Input
                  type="password"
                  placeholder="••••••••"
                  {...loginForm.register('password')}
                />
                {loginForm.formState.errors.password && (
                  <p className="text-xs text-red-500">{loginForm.formState.errors.password.message}</p>
                )}
              </div>
              {message && (
                <p className={`text-sm ${message.type === 'error' ? 'text-red-500' : 'text-green-500'}`}>
                  {message.text}
                </p>
              )}
            </CardContent>
            <CardFooter>
              <Button type="submit" className="w-full" disabled={isLoading}>
                {isLoading ? '登录中...' : '登录'}
              </Button>
            </CardFooter>
          </form>
        </TabsContent>

        <TabsContent value="register">
          <form onSubmit={registerForm.handleSubmit(handleRegister)}>
            <CardContent className="space-y-4">
              <div className="space-y-2">
                <label className="text-sm font-medium">姓名</label>
                <Input
                  placeholder="你的名字"
                  {...registerForm.register('name')}
                />
                {registerForm.formState.errors.name && (
                  <p className="text-xs text-red-500">{registerForm.formState.errors.name.message}</p>
                )}
              </div>
              <div className="space-y-2">
                <label className="text-sm font-medium">邮箱</label>
                <Input
                  type="email"
                  placeholder="your@email.com"
                  {...registerForm.register('email')}
                />
                {registerForm.formState.errors.email && (
                  <p className="text-xs text-red-500">{registerForm.formState.errors.email.message}</p>
                )}
              </div>
              <div className="space-y-2">
                <label className="text-sm font-medium">密码</label>
                <Input
                  type="password"
                  placeholder="••••••••"
                  {...registerForm.register('password')}
                />
                {registerForm.formState.errors.password && (
                  <p className="text-xs text-red-500">{registerForm.formState.errors.password.message}</p>
                )}
              </div>
              <div className="space-y-2">
                <label className="text-sm font-medium">确认密码</label>
                <Input
                  type="password"
                  placeholder="••••••••"
                  {...registerForm.register('confirmPassword')}
                />
                {registerForm.formState.errors.confirmPassword && (
                  <p className="text-xs text-red-500">{registerForm.formState.errors.confirmPassword.message}</p>
                )}
              </div>
              {message && (
                <p className={`text-sm ${message.type === 'error' ? 'text-red-500' : 'text-green-500'}`}>
                  {message.text}
                </p>
              )}
            </CardContent>
            <CardFooter>
              <Button type="submit" className="w-full" disabled={isLoading}>
                {isLoading ? '注册中...' : '注册'}
              </Button>
            </CardFooter>
          </form>
        </TabsContent>
      </Tabs>
    </Card>
  )
}