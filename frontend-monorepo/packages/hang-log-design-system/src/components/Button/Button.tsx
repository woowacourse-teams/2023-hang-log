import type { Size } from '@type/index';
import type { ComponentPropsWithRef, ForwardedRef } from 'react';
import { forwardRef } from 'react';

import { buttonStyling, getSizeStyling, getVariantStyling } from '@components/Button/Button.style';

export interface ButtonProps extends ComponentPropsWithRef<'button'> {
  size?: Extract<Size, 'small' | 'medium' | 'large'>;
  variant?: 'primary' | 'secondary' | 'default' | 'outline' | 'text' | 'danger';
}

const Button = (
  { size = 'medium', variant = 'default', children, ...attributes }: ButtonProps,
  ref: ForwardedRef<HTMLButtonElement>
) => {
  return (
    // eslint-disable-next-line react/button-has-type
    <button
      ref={ref}
      css={[buttonStyling, getVariantStyling(variant), getSizeStyling(size)]}
      {...attributes}
    >
      {children}
    </button>
  );
};

export default forwardRef(Button);
