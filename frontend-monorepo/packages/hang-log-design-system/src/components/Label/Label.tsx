import type { ComponentPropsWithoutRef } from 'react';

import { labelStyling, requiredStyling } from '@components/Label/Label.style';

export interface LabelProps extends ComponentPropsWithoutRef<'label'> {
  required?: boolean;
}

const Label = ({ id, required = false, children, ...attributes }: LabelProps) => {
  return (
    <label css={labelStyling} htmlFor={id} {...attributes}>
      {children}{' '}
      {required && (
        <span css={requiredStyling} aria-hidden>
          *
        </span>
      )}
    </label>
  );
};

export default Label;
