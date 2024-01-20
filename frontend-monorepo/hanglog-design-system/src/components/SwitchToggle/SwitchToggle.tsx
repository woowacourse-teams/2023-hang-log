import type { ChangeEvent, ComponentPropsWithoutRef } from 'react';

import { switchToggleStyling } from './SwitchToggle.style';

export interface SwitchToggleProps extends ComponentPropsWithoutRef<'input'> {
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  checkedState: boolean;
}

const SwitchToggle = ({ onChange, checkedState, ...attributes }: SwitchToggleProps) => {
  return (
    <input
      type="checkbox"
      role="switch"
      aria-checked={checkedState}
      checked={checkedState}
      onChange={onChange}
      {...attributes}
      css={switchToggleStyling}
    />
  );
};

export default SwitchToggle;
