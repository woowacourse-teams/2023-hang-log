import type { ComponentPropsWithoutRef, ReactNode } from 'react';
import { Link } from 'react-router-dom';

import { buttonStyling } from '@components/common/KakaoButton/KakaoButton.style';

import { KAKAO_AUTH_API_URL } from '@constants/api';

interface KakaoButtonProps extends ComponentPropsWithoutRef<'a'> {
  children: ReactNode;
}

const KakaoButton = ({ children, ...attributes }: KakaoButtonProps) => {
  return (
    <Link to={KAKAO_AUTH_API_URL} css={buttonStyling} {...attributes}>
      {children}
    </Link>
  );
};

export default KakaoButton;
