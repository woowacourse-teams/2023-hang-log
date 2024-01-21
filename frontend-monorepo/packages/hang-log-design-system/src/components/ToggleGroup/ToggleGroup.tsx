import type { ComponentPropsWithRef, ForwardedRef } from 'react';
import { forwardRef } from 'react';

import { containerStyling } from '@components/ToggleGroup/ToggleGroup.style';

export interface ToggleGroupProps extends ComponentPropsWithRef<'ul'> {}

const ToggleGroup = (
  { children, ...attributes }: ToggleGroupProps,
  ref: ForwardedRef<HTMLUListElement>
) => {
  return (
    <ul role="radiogroup" tabIndex={-1} css={containerStyling} ref={ref} {...attributes}>
      {children}
    </ul>
  );
};

export default forwardRef(ToggleGroup);
