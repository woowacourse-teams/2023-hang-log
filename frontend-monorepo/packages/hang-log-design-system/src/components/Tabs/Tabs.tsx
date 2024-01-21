import type { ComponentPropsWithRef, ForwardedRef } from 'react';
import { forwardRef } from 'react';

import { containerStyling } from '@components/Tabs/Tabs.style';

export type TabsProps = ComponentPropsWithRef<'ul'>;

const Tabs = ({ children, ...attributes }: TabsProps, ref: ForwardedRef<HTMLUListElement>) => {
  return (
    <ul role="tablist" tabIndex={-1} css={containerStyling} ref={ref} {...attributes}>
      {children}
    </ul>
  );
};

export default forwardRef(Tabs);
