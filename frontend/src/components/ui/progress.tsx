import * as React from 'react'
import { Root as ProgressRoot, Indicator as ProgressIndicator } from '@radix-ui/react-progress'
import { cn } from '@/lib/utils'

interface ProgressProps extends React.ComponentPropsWithoutRef<typeof ProgressRoot> {
  value?: number
}

const Progress = React.forwardRef<React.ElementRef<typeof ProgressRoot>, ProgressProps>(
  ({ className, value, ...props }, ref) => (
    <ProgressRoot
      ref={ref}
      className={cn(
        'relative h-4 w-full overflow-hidden rounded-full bg-secondary',
        className
      )}
      {...props}
    >
      <ProgressIndicator
        className="h-full w-full flex-1 bg-primary transition-all"
        style={{ transform: `translateX(-${100 - (value || 0)}%)` }}
      />
    </ProgressRoot>
  )
)
Progress.displayName = 'Progress'

export { Progress }