import type { Size } from '@type/index';
import type { ComponentPropsWithoutRef } from 'react';

import * as S from '@components/Text/Text.style';

export interface TextProps extends ComponentPropsWithoutRef<'p'> {
  size?: Extract<Size, 'xSmall' | 'small' | 'medium' | 'large'>;
}

const Text = ({ size = 'medium', children, ...attributes }: TextProps) => {
  return (
    <p css={S.getSizeStyling(size)} {...attributes}>
      {children}
    </p>
  );
};

export default Text;
