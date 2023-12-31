import { Navigate } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Flex, Heading, Theme } from 'hang-log-design-system';

import {
  backgroundImageStyling,
  buttonContainerStyling,
  containerStyling,
  headingStyling,
} from '@pages/LogInPage/LogInPage.style';

import GoogleButton from '@components/common/GoogleButton/GoogleButton';
import KakaoButton from '@components/common/KakaoButton/KakaoButton';

import { isLoggedInState } from '@store/auth';

import { PATH } from '@constants/path';

import AuthImage from '@assets/svg/auth-image.svg';
import LogoVertical from '@assets/svg/logo-vertical.svg';

const LogInPage = () => {
  const isLoggedIn = useRecoilValue(isLoggedInState);

  if (isLoggedIn) return <Navigate to={PATH.ROOT} />;

  return (
    <Flex
      styles={{ direction: 'column', justify: 'center', align: 'center' }}
      css={containerStyling}
    >
      <LogoVertical />
      <Heading size="small" css={headingStyling}>
        나만의 여행을 기록해 보세요
      </Heading>
      <Flex
        styles={{ direction: 'column', gap: Theme.spacer.spacing1, align: 'stretch' }}
        css={buttonContainerStyling}
      >
        <KakaoButton>카카오로 계속하기</KakaoButton>
        <GoogleButton>구글로 계속하기</GoogleButton>
      </Flex>
      <AuthImage aria-label="세계 랜드마크 이미지" css={backgroundImageStyling} />
    </Flex>
  );
};

export default LogInPage;
