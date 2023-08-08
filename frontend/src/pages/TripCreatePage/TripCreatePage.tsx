import CreatePageImage from '@assets/svg/create-page-image.svg';
import { mediaQueryMobileState } from '@store/mediaQuery';
import { Box, Flex, Heading } from 'hang-log-design-system';
import { useRecoilValue } from 'recoil';

import {
  backgroundImage,
  boxStyling,
  containerStyling,
} from '@pages/TripCreatePage/TripCreatePage.style';

import TripCreateForm from '@components/trip/TripCreateForm/TripCreateForm';

const TripCreatePage = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <Flex css={containerStyling}>
      <Box css={boxStyling}>
        <Heading size={isMobile ? 'small' : 'medium'}>여행을 기록해 보세요</Heading>
        <TripCreateForm />
      </Box>
      <CreatePageImage aria-label="세계 랜드마크 이미지" css={backgroundImage} />
    </Flex>
  );
};

export default TripCreatePage;
