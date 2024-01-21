import type { ComponentPropsWithoutRef, ElementType } from 'react';

import type { FlexStylingProps } from '@components/Flex/Flex.style';
import { getFlexStyling } from '@components/Flex/Flex.style';

export interface FlexProps extends ComponentPropsWithoutRef<'div'> {
  /**
   * Flex 컴포넌트가 사용할 HTML 태그
   *
   * @default 'div'
   */
  tag?: ElementType;
  /** Flex 컴포넌트 스타일 옵션 */
  styles?: FlexStylingProps;
}

const Flex = ({ tag = 'div', styles = {}, children, ...attributes }: FlexProps) => {
  const Tag = tag;

  return (
    <Tag css={getFlexStyling(styles)} {...attributes}>
      {children}
    </Tag>
  );
};

export default Flex;
