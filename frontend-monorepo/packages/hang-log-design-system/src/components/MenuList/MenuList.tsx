import type { ComponentPropsWithRef, ForwardedRef } from 'react';
import { forwardRef } from 'react';

import { menuListStyling } from '@components/MenuList/MenuList.style';

type MenuListProps = ComponentPropsWithRef<'ul'>;

const MenuList = (
  { children, ...attributes }: MenuListProps,
  ref: ForwardedRef<HTMLUListElement>
) => {
  return (
    <ul css={menuListStyling} ref={ref} {...attributes}>
      {children}
    </ul>
  );
};

export default forwardRef(MenuList);
