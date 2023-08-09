import { KAKAO_AUTH_API_URL } from '@constants/api';
import type { ComponentPropsWithoutRef, ReactNode } from 'react';
import { Link } from 'react-router-dom';

import { buttonStyling } from '@components/common/GoogleButton/GoogleButton.style';

interface GoogleButtonProps extends ComponentPropsWithoutRef<'a'> {
  children: ReactNode;
}

const GoogleButton = ({ children, ...attributes }: GoogleButtonProps) => {
  return (
    <Link to={KAKAO_AUTH_API_URL} css={buttonStyling} {...attributes}>
      {children}
    </Link>
  );
};

export default GoogleButton;
