'use client'

import { useQuery, useMutation, useQueryClient, QueryKey } from '@tanstack/react-query'
import { apiGet, apiPost, apiPut, apiDelete } from '@/lib/api'

export function useApi<T>(
  queryKey: QueryKey,
  url: string,
  params?: Record<string, unknown>,
  options?: {
    enabled?: boolean
  }
) {
  return useQuery<T, Error>({
    queryKey: [queryKey, params],
    queryFn: async () => {
      const response = await apiGet<T>(url, params)
      if (response.data.code === 200 || response.data.code === 0) {
        return response.data.data as T
      }
      throw new Error(response.data.message || 'API Error')
    },
    enabled: options?.enabled ?? true,
  })
}

export function useApiMutation<T, D = unknown>(
  url: string,
  method: 'post' | 'put' | 'delete' = 'post',
  queryKeyToInvalidate?: QueryKey
) {
  const queryClient = useQueryClient()

  const mutationFn = async (data?: D) => {
    let response
    switch (method) {
      case 'post':
        response = await apiPost<T>(url, data)
        break
      case 'put':
        response = await apiPut<T>(url, data)
        break
      case 'delete':
        response = await apiDelete<T>(url)
        break
      default:
        response = await apiPost<T>(url, data)
    }
    if (response.data.code === 200 || response.data.code === 0) {
      return response.data.data as T
    }
    throw new Error(response.data.message || 'API Error')
  }

  return useMutation<T, Error, D>({
    mutationFn,
    onSuccess: () => {
      if (queryKeyToInvalidate) {
        queryClient.invalidateQueries({ queryKey: [queryKeyToInvalidate] })
      }
    },
  })
}