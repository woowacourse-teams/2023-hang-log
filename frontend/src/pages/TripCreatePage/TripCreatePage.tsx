import { Suspense } from 'react';

import { useRecoilValue } from 'recoil';

import { Box, Flex, Heading } from 'hang-log-design-system';

import {
  backgroundImageStyling,
  boxStyling,
  containerStyling,
} from '@pages/TripCreatePage/TripCreatePage.style';

import TripCreateForm from '@components/trip/TripCreateForm/TripCreateForm';
import TripCreateFormSkeleton from '@components/trip/TripCreateForm/TripCreateFormSkeleton';

import { mediaQueryMobileState } from '@store/mediaQuery';

import CreatePageImage from '@assets/svg/create-page-image.svg';

const TripCreatePage = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <Flex css={containerStyling}>
      <Box css={boxStyling}>
        <Heading size={isMobile ? 'small' : 'medium'}>여행을 기록해 보세요</Heading>
        <Suspense fallback={<TripCreateFormSkeleton />}>
          <TripCreateForm />
        </Suspense>
      </Box>
      <CreatePageImage aria-label="세계 랜드마크 이미지" css={backgroundImageStyling} />
    </Flex>
  );
};

export default TripCreatePage;
