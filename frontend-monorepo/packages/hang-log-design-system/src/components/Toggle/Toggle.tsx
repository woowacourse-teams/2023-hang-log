import type { ComponentPropsWithRef, ForwardedRef, KeyboardEvent } from 'react';
import { forwardRef } from 'react';

import { getToggleStyling } from '@components/Toggle/Toggle.style';

export interface ToggleProps extends ComponentPropsWithRef<'li'> {
  toggleId: string | number;
  selectedId: string | number;
  text: string;
  changeSelect: (toggleId: string | number) => void;
}

const Toggle = (
  { toggleId, text, selectedId, changeSelect, ...attributes }: ToggleProps,
  ref?: ForwardedRef<HTMLLIElement>
) => {
  const handleEnterKeyPress = (event: KeyboardEvent<HTMLLIElement>) => {
    if (event.key === 'Enter') {
      changeSelect(toggleId);
    }
  };

  return (
    <li
      // eslint-disable-next-line jsx-a11y/no-noninteractive-element-to-interactive-role
      role="radio"
      tabIndex={0}
      ref={ref}
      css={getToggleStyling(selectedId === toggleId)}
      aria-checked={selectedId === toggleId}
      onClick={() => changeSelect(toggleId)}
      onKeyDown={handleEnterKeyPress}
      {...attributes}
    >
      {text}
    </li>
  );
};

export default forwardRef(Toggle);
