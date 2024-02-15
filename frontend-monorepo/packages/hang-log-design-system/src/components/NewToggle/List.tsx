import type { ComponentPropsWithRef, ForwardedRef, KeyboardEvent } from 'react';
import { useContext, forwardRef } from 'react';

import { getListStyling } from '@components/NewToggle/Toggle.style';
import { NewToggleContext } from '@components/NewToggle/NewToggle';

export interface ToggleProps extends ComponentPropsWithRef<'li'> {
  toggleKey: number | string;
  text: string;
}

const List = (
  { toggleKey, text, ...attributes }: ToggleProps,
  ref?: ForwardedRef<HTMLLIElement>
) => {
  const context = useContext(NewToggleContext);

  if (!context) throw new Error('NewToggle 컴포넌트가 Wrapping되어있지 않습니다.');

  const { selectKey, handleSelect } = context;

  const handleEnterKeyPress = (event: KeyboardEvent<HTMLLIElement>) => {
    if (event.key === 'Enter') {
      handleSelect(toggleKey);
    }
  };

  return (
    <li
      // eslint-disable-next-line jsx-a11y/no-noninteractive-element-to-interactive-role
      role="radio"
      aria-label={`${text}로 토글할 수 있는 버튼입니다. ${
        selectKey === toggleKey ? '이미 선택되어있습니다.' : ''
      }`}
      tabIndex={0}
      ref={ref}
      css={getListStyling(selectKey === toggleKey)}
      aria-checked={selectKey === toggleKey}
      onClick={() => handleSelect(toggleKey)}
      onKeyDown={handleEnterKeyPress}
      {...attributes}
    >
      {text}
    </li>
  );
};

export default forwardRef(List);
