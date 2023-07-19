import CreatePageImage from '@assets/svg/create-page-image.svg';
import { Box, Button, Flex, Heading } from 'hang-log-design-system';

import {
  backgroundImage,
  boxStyling,
  containerStyling,
} from '@pages/TripCreatePage/TripCreatePage.style';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';

const TripCreatePage = () => {
  return (
    <Flex css={containerStyling}>
      <Box css={boxStyling}>
        <Heading>여행을 기록해 보세요</Heading>
        <Flex
          styles={{
            width: '100%',
            direction: 'column',
            gap: '24px',
            marginTop: '32px',
          }}
        >
          <CitySearchBar />
          <DateInput />
          <Button variant="primary" style={{ width: '400px' }}>
            기록하기
          </Button>
        </Flex>
      </Box>

      <CreatePageImage aria-label="세계 랜드마크 이미지" css={backgroundImage} />
    </Flex>
  );
};

export default TripCreatePage;
