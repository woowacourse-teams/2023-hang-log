import CheckedIcon from '@assets/svg/checked-icon.svg';
import UncheckedIcon from '@assets/svg/unchecked-icon.svg';
import type { ComponentPropsWithRef, ForwardedRef } from 'react';
import { forwardRef, useCallback, useState } from 'react';

import { checkboxStyling, inputStyling } from '@components/Checkbox/Checkbox.style';

export interface CheckboxProps extends ComponentPropsWithRef<'input'> {
  label?: string;
  isChecked?: boolean;
}

const Checkbox = (
  { id, label = '', isChecked = false, ...attributes }: CheckboxProps,
  ref: ForwardedRef<HTMLInputElement>
) => {
  const [checked, setChecked] = useState(isChecked);

  const handleChecked = useCallback(() => {
    setChecked(!checked);
  }, [checked]);

  return (
    <label css={checkboxStyling} htmlFor={id}>
      <input
        css={inputStyling}
        id={id}
        checked={checked}
        type="checkbox"
        {...attributes}
        ref={ref}
      />
      {checked ? (
        <CheckedIcon onClick={handleChecked} />
      ) : (
        <UncheckedIcon onClick={handleChecked} />
      )}
      {label && <span>{label}</span>}
    </label>
  );
};

export default forwardRef(Checkbox);
