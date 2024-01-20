import type { ComponentPropsWithoutRef } from 'react';

import { getDirectionStyling } from '@components/Divider/Divider.style';

export interface DividerProps extends ComponentPropsWithoutRef<'div'> {
  length?: string;
  direction?: 'horizontal' | 'vertical';
}

const Divider = ({ length = '100%', direction = 'horizontal', ...attributes }: DividerProps) => {
  return <div css={getDirectionStyling(direction, length)} {...attributes} />;
};

export default Divider;
