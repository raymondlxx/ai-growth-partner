'use client'

import { createContext, useContext, useState, useEffect, useCallback, ReactNode } from 'react'
import { getToken, setToken, clearToken } from '@/lib/auth'
import { apiPost } from '@/lib/api'
import { User } from '@/types'

interface LoginCredentials {
  email: string
  password: string
}

interface RegisterData {
  email: string
  password: string
  name: string
}

interface AuthContextType {
  user: User | null
  isLoading: boolean
  isAuthenticated: boolean
  login: (credentials: LoginCredentials) => Promise<{ success: boolean; message: string }>
  register: (data: RegisterData) => Promise<{ success: boolean; message: string }>
  logout: () => void
  refreshUser: () => Promise<void>
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null)
  const [isLoading, setIsLoading] = useState(true)

  const refreshUser = useCallback(async () => {
    const token = getToken()
    if (!token) {
      setUser(null)
      setIsLoading(false)
      return
    }

    try {
      const response = await apiPost<User>('/user/profile')
      if (response.data.code === 200) {
        setUser(response.data.data)
      } else {
        clearToken()
        setUser(null)
      }
    } catch {
      clearToken()
      setUser(null)
    } finally {
      setIsLoading(false)
    }
  }, [])

  useEffect(() => {
    refreshUser()
  }, [refreshUser])

  const login = async (credentials: LoginCredentials): Promise<{ success: boolean; message: string }> => {
    try {
      const response = await apiPost<{ token: string; user: User }>('/auth/login', credentials)
      if (response.data.code === 200) {
        setToken(response.data.data.token)
        setUser(response.data.data.user)
        return { success: true, message: '登录成功' }
      }
      return { success: false, message: response.data.message || '登录失败' }
    } catch (error: unknown) {
      const err = error as { response?: { data?: { message?: string } } }
      return { success: false, message: err.response?.data?.message || '网络错误，请稍后重试' }
    }
  }

  const register = async (data: RegisterData): Promise<{ success: boolean; message: string }> => {
    try {
      const response = await apiPost<{ token: string; user: User }>('/auth/register', data)
      if (response.data.code === 200) {
        setToken(response.data.data.token)
        setUser(response.data.data.user)
        return { success: true, message: '注册成功' }
      }
      return { success: false, message: response.data.message || '注册失败' }
    } catch (error: unknown) {
      const err = error as { response?: { data?: { message?: string } } }
      return { success: false, message: err.response?.data?.message || '网络错误，请稍后重试' }
    }
  }

  const logout = () => {
    clearToken()
    setUser(null)
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        isLoading,
        isAuthenticated: !!user,
        login,
        register,
        logout,
        refreshUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth(): AuthContextType {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
}