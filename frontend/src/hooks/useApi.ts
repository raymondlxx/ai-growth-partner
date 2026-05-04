'use client'

import { useQuery, useMutation, useQueryClient, QueryKey } from '@tanstack/react-query'
import { apiGet, apiPost, apiPut, apiDelete } from '@/lib/api'
import { ApiResponse } from '@/types'

export function useApi<T>(
  queryKey: QueryKey,
  url: string,
  params?: Record<string, unknown>,
  options?: {
    enabled?: boolean
  }
) {
  return useQuery<ApiResponse<T>>({
    queryKey: [queryKey, params],
    queryFn: async () => {
      const response = await apiGet<T>(url, params)
      return response.data
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
    switch (method) {
      case 'post':
        return apiPost<T>(url, data)
      case 'put':
        return apiPut<T>(url, data)
      case 'delete':
        return apiDelete<T>(url)
      default:
        return apiPost<T>(url, data)
    }
  }

  return useMutation<ApiResponse<T>, Error, D>({
    mutationFn,
    onSuccess: () => {
      if (queryKeyToInvalidate) {
        queryClient.invalidateQueries({ queryKey: [queryKeyToInvalidate] })
      }
    },
  })
}