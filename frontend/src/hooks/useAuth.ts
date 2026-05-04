'use client'

import { useState, useEffect, useCallback } from 'react'
import { getToken, setToken, clearToken, isAuthenticated } from '@/lib/auth'
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

export function useAuth() {
  const [user, setUser] = useState<User | null>(null)
  const [isLoading, setIsLoading] = useState(true)

  const checkAuth = useCallback(async () => {
    const token = getToken()
    if (!token) {
      setIsLoading(false)
      return
    }

    try {
      // In a real app, you'd validate the token with the backend
      // For now, we'll decode from localStorage or fetch user profile
      const response = await apiPost<User>('/auth/me')
      if (response.data.code === 200) {
        setUser(response.data.data)
      }
    } catch (error) {
      console.error('Auth check failed:', error)
      clearToken()
    } finally {
      setIsLoading(false)
    }
  }, [])

  useEffect(() => {
    checkAuth()
  }, [checkAuth])

  const login = async (credentials: LoginCredentials): Promise<{ success: boolean; message: string }> => {
    try {
      const response = await apiPost<{ token: string; user: User }>('/auth/login', credentials)
      if (response.data.code === 200) {
        setToken(response.data.data.token)
        setUser(response.data.data.user)
        return { success: true, message: '登录成功' }
      }
      return { success: false, message: response.data.message }
    } catch (error: any) {
      return { success: false, message: error.response?.data?.message || '登录失败' }
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
      return { success: false, message: response.data.message }
    } catch (error: any) {
      return { success: false, message: error.response?.data?.message || '注册失败' }
    }
  }

  const logout = () => {
    clearToken()
    setUser(null)
  }

  return {
    user,
    isLoading,
    isAuthenticated: isAuthenticated(),
    login,
    register,
    logout,
  }
}