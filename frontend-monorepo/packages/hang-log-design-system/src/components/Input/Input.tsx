import type { Size } from '@type/index';
import type { ComponentPropsWithRef, ForwardedRef, ReactElement } from 'react';
import { forwardRef } from 'react';

import {
  getInputStyling,
  getSizeStyling,
  getVariantStyling,
  inputContainerStyling,
  inputWrapperStyling,
} from '@components/Input/Input.style';
import Label from '@components/Label/Label';
import SupportingText from '@components/SupportingText/SupportingText';

export interface InputProps extends Omit<ComponentPropsWithRef<'input'>, 'size'> {
  label?: string;
  variant?: 'default' | 'text';
  size?: Extract<Size, 'small' | 'medium' | 'large'>;
  isError?: boolean;
  icon?: ReactElement;
  supportingText?: string;
}

const Input = (
  {
    label,
    variant = 'default',
    size = 'medium',
    isError = false,
    icon,
    supportingText,
    ...attributes
  }: InputProps,
  ref: ForwardedRef<HTMLInputElement>
) => {
  return (
    <div css={inputContainerStyling}>
      {label && (
        <Label id={attributes.id} required={attributes.required}>
          {label}
        </Label>
      )}
      <div css={[getSizeStyling(size), inputWrapperStyling(isError), getVariantStyling(variant)]}>
        {icon}
        <input ref={ref} css={[getSizeStyling(size), getInputStyling]} {...attributes} />
      </div>
      {supportingText && <SupportingText isError={isError}>{supportingText}</SupportingText>}
    </div>
  );
};

export default forwardRef(Input);
