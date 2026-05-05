import axios, { AxiosError, InternalAxiosRequestConfig } from 'axios'
import { getToken, clearToken } from './auth'

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost/api'

export const api = axios.create({
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
      if (typeof window !== 'undefined') {
        window.location.href = '/login'
      }
    }
    return Promise.reject(error)
  }
)

// API helper functions
export const apiGet = <T>(url: string, params?: Record<string, unknown>) =>
  api.get<{ code: number; data: T; message: string }>(url, { params })

export const apiPost = <T>(url: string, data?: unknown) =>
  api.post<{ code: number; data: T; message: string }>(url, data)

export const apiPut = <T>(url: string, data?: unknown) =>
  api.put<{ code: number; data: T; message: string }>(url, data)

export const apiDelete = <T>(url: string) =>
  api.delete<{ code: number; data: T; message: string }>(url)

// Auth API
export const authApi = {
  login: (email: string, password: string) => apiPost<{ token: string; user: unknown }>('/auth/login', { email, password }),
  register: (data: { email: string; password: string; name: string }) => 
    apiPost<{ token: string; user: unknown }>('/auth/register', data),
  getProfile: () => apiPost<unknown>('/user/profile'),
  logout: () => apiPost<void>('/auth/logout'),
}

// User API
export const userApi = {
  getProfile: () => apiGet<unknown>('/user/profile'),
  updateProfile: (data: unknown) => apiPut<unknown>('/user/profile', data),
}

// Task API
export const taskApi = {
  getTasks: (params?: { category?: string; completed?: boolean }) =>
    apiGet<{ tasks: unknown[]; total: number }>('/task/list', params as Record<string, unknown>),
  getTask: (id: string) => apiGet<unknown>(`/task/${id}`),
  completeTask: (taskId: string, proof?: string) =>
    apiPost<{ xpEarned: number; newLevel: number; badgeEarned?: string }>('/task/complete', { taskId, proof }),
}

// Path API
export const pathApi = {
  getPaths: () => apiGet<{ paths: unknown[] }>('/path/list'),
  getPath: (id: string) => apiGet<unknown>(`/path/${id}`),
  enrollPath: (id: string) => apiPost<unknown>('/path/enroll', { pathId: id }),
}

// Knowledge API
export const knowledgeApi = {
  search: (params: { q?: string; tags?: string[]; page?: number; pageSize?: number }) =>
    apiGet<{ articles: unknown[]; total: number }>('/knowledge/search', params as Record<string, unknown>),
  getArticle: (id: string) => apiGet<unknown>(`/knowledge/${id}`),
}

// Mentor API
export const mentorApi = {
  getSessions: () => apiGet<{ sessions: unknown[] }>('/mentor/sessions'),
  getSession: (id: string) => apiGet<{ messages: unknown[] }>(`/mentor/session/${id}`),
  sendMessage: (sessionId: string, content: string) =>
    apiPost<{ message: unknown; emotion?: string }>(`/mentor/session/${sessionId}/message`, { content }),
  createSession: () => apiPost<{ sessionId: string }>('/mentor/session'),
}

export default api