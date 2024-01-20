import { useCallback, useEffect, useRef } from 'react';
import type { ComponentPropsWithoutRef } from 'react';

import { menuStyling } from '@components/Menu/Menu.style';

export interface MenuProps extends ComponentPropsWithoutRef<'div'> {
  closeMenu: () => void;
}

const Menu = ({ children, closeMenu, ...attributes }: MenuProps) => {
  const menuRef = useRef<HTMLDivElement>(null);

  const handleBackdropClick = useCallback(
    (event: globalThis.MouseEvent) => {
      if (!menuRef.current?.contains(event.target as Node)) {
        closeMenu();
      }
    },
    [closeMenu]
  );

  const handleEscClick = useCallback(
    (event: globalThis.KeyboardEvent) => {
      if (event.key === 'Escape') {
        closeMenu();
      }
    },
    [closeMenu]
  );

  useEffect(() => {
    window.addEventListener('click', handleBackdropClick);
    window.addEventListener('keydown', handleEscClick);

    return () => {
      window.removeEventListener('click', handleBackdropClick);
      window.removeEventListener('keydown', handleEscClick);
    };
  }, [handleBackdropClick, handleEscClick]);

  return (
    <div ref={menuRef} css={menuStyling} {...attributes}>
      {children}
    </div>
  );
};

export default Menu;
