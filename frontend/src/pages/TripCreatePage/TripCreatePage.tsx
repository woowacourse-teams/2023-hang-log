import CreatePageImage from '@assets/svg/create-page-image.svg';
import { Box, Flex, Heading } from 'hang-log-design-system';

import { useCityQuery } from '@hooks/api/useCityQuery';

import {
  backgroundImage,
  boxStyling,
  containerStyling,
} from '@pages/TripCreatePage/TripCreatePage.style';

import NewTripForm from '@components/newTrip/NewTripForm/NewTripForm';

const TripCreatePage = () => {
  useCityQuery();

  return (
    <Flex css={containerStyling}>
      <Box css={boxStyling}>
        <Heading>여행을 기록해 보세요</Heading>
        <NewTripForm />
      </Box>

      <CreatePageImage aria-label="세계 랜드마크 이미지" css={backgroundImage} />
    </Flex>
  );
};

export default TripCreatePage;
