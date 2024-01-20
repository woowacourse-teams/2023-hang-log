import type { ComponentPropsWithoutRef } from 'react';

import { getSkeletonStyling } from '@components/Skeleton/Skeleton.style';

export interface SkeletonProps extends ComponentPropsWithoutRef<'div'> {
  width?: string;
  height?: string;
  /**
   * Skeleton 모양
   *
   * @default 'square'
   */
  variant?: 'square' | 'circle';
}

const Skeleton = ({
  width = '100%',
  height = '24px',
  variant = 'square',
  className = '',
  ...attributes
}: SkeletonProps) => {
  return (
    <div
      css={getSkeletonStyling(width, height, variant)}
      className={`skeleton ${className}`}
      {...attributes}
    />
  );
};

export default Skeleton;
