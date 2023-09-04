import { useNavigate } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Button, Flex, Heading, Theme } from 'hang-log-design-system';

import {
  containerStyling,
  headingStyling,
  imageStyling,
  titleStyling,
} from '@pages/IntroPage/IntroPage.style';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { PATH } from '@constants/path';

import SampleTripImage from '@assets/png/sample-trip-image.png';
import SampleTripImageMobile from '@assets/png/sample-trip-image_mobile.png';

const IntroPage = () => {
  const navigate = useNavigate();

  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <Flex
      styles={{ direction: 'column', align: 'center', gap: Theme.spacer.spacing4 }}
      css={containerStyling}
    >
      <h1 css={titleStyling}>
        나만의 여행 기록, <span>행록</span>
      </h1>
      <Heading size="xSmall" css={headingStyling}>
        여행과 관련된 모든 정보를 <br />
        한곳에서 기록해 보세요.
      </Heading>
      <Button variant="primary" onClick={() => navigate(PATH.LOGIN)}>
        시작하기
      </Button>
      <img
        src={isMobile ? SampleTripImageMobile : SampleTripImage}
        alt="샘플 여행 기록"
        css={imageStyling}
      />
    </Flex>
  );
};

export default IntroPage;
