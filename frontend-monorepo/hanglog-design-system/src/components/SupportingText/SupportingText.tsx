import type { ComponentPropsWithoutRef } from 'react';

import { getTextStyling } from '@components/SupportingText/SupportingText.style';

export interface SupportingTextProps extends ComponentPropsWithoutRef<'span'> {
  isError?: boolean;
}

const SupportingText = ({ isError = false, children, ...attributes }: SupportingTextProps) => {
  return (
    <span css={getTextStyling(isError)} {...attributes}>
      {children}
    </span>
  );
};

export default SupportingText;
