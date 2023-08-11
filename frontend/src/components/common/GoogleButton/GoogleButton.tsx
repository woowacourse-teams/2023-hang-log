import type { ComponentPropsWithoutRef, ReactNode } from 'react';
import { Link } from 'react-router-dom';

import { buttonStyling } from '@components/common/GoogleButton/GoogleButton.style';

import { GOOGLE_AUTH_API_URL } from '@constants/api';

interface GoogleButtonProps extends ComponentPropsWithoutRef<'a'> {
  children: ReactNode;
}

const GoogleButton = ({ children, ...attributes }: GoogleButtonProps) => {
  return (
    <Link to={GOOGLE_AUTH_API_URL} css={buttonStyling} {...attributes}>
      {children}
    </Link>
  );
};

export default GoogleButton;
