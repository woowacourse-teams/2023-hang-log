import type { ComponentPropsWithRef, ForwardedRef, KeyboardEvent } from 'react';
import { forwardRef, useCallback } from 'react';

import { menuItemStyling } from '@components/MenuItem/MenuItem.style';

interface MenuItemProps extends ComponentPropsWithRef<'li'> {
  /** 메뉴 아이템을 클릭했을 때 실행시킬 함수  */
  onClick: () => void;
}

const MenuItem = (
  { children, onClick, ...attributes }: MenuItemProps,
  ref: ForwardedRef<HTMLLIElement>
) => {
  const handleEnterKeyPress = useCallback(
    (event: KeyboardEvent<HTMLLIElement>) => {
      if (event.key === 'Enter') {
        onClick();
      }
    },
    [onClick]
  );

  return (
    <li
      // eslint-disable-next-line jsx-a11y/no-noninteractive-element-to-interactive-role
      role="button"
      css={menuItemStyling}
      ref={ref}
      tabIndex={0}
      onClick={onClick}
      onKeyDown={handleEnterKeyPress}
      {...attributes}
    >
      {children}
    </li>
  );
};

export default forwardRef(MenuItem);
