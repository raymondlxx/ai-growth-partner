import axios, { AxiosError, InternalAxiosRequestConfig } from 'axios'
import { getToken, clearToken } from './auth'
import { useRouter } from 'next/navigation'

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
})

// Request interceptor to add JWT token
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = getToken()
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error: AxiosError) => {
    return Promise.reject(error)
  }
)

// Response interceptor to handle 401 errors
api.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response?.status === 401) {
      clearToken()
      // Redirect to login page
      if (typeof window !== 'undefined') {
        window.location.href = '/login'
      }
    }
    return Promise.reject(error)
  }
)

export default api

// API helper functions
export const apiGet = <T>(url: string, params?: Record<string, unknown>) =>
  api.get<{ code: number; data: T; message: string }>(url, { params })

export const apiPost = <T>(url: string, data?: unknown) =>
  api.post<{ code: number; data: T; message: string }>(url, data)

export const apiPut = <T>(url: string, data?: unknown) =>
  api.put<{ code: number; data: T; message: string }>(url, data)

export const apiDelete = <T>(url: string) =>
  api.delete<{ code: number; data: T; message: string }>(url)