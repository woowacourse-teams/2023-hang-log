import type { ComponentPropsWithoutRef, ElementType } from 'react';

import { getCenterStyling } from '@components/Center/Center.style';

export interface CenterProps extends ComponentPropsWithoutRef<'div'> {
  /**
   * Center 컴포넌트가 사용할 HTML 태그
   *
   * @default 'div'
   */
  tag?: ElementType;
}

const Center = ({ tag = 'div', children, ...attributes }: CenterProps) => {
  const Tag = tag;

  return (
    <Tag css={getCenterStyling} {...attributes}>
      {children}
    </Tag>
  );
};

export default Center;
