import CreatePageImage from '@assets/svg/create-page-image.svg';
import { CityData } from '@type/city';
import { DateRangeData, NewTripData } from '@type/trips';
import { Box, Button, Flex, Heading } from 'hang-log-design-system';
import type { FormEvent } from 'react';
import { useEffect, useState } from 'react';

import {
  backgroundImage,
  boxStyling,
  containerStyling,
  formStyling,
} from '@pages/TripCreatePage/TripCreatePage.style';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';

const initialNewTripData = {
  startDate: null,
  endDate: null,
  cityIds: [],
};

const TripCreatePage = () => {
  const [newTripData, setNewTripData] = useState<NewTripData>(initialNewTripData);
  const [isAllInputFilled, setIsAllInputFilled] = useState(false);

  useEffect(() => {
    validateInputs();
  }, [newTripData]);

  const setCityData = (cities: CityData[]) => {
    const cityIds = cities.map((city) => city.id);

    setNewTripData((prev) => ({ ...prev, cityIds }));
  };

  const setDateData = (dateRange: DateRangeData) => {
    const { start: startDate, end: endDate } = dateRange;

    setNewTripData((prev) => ({ ...prev, startDate, endDate }));
  };

  const validateInputs = () => {
    const { startDate, endDate, cityIds } = newTripData;

    setIsAllInputFilled(!!startDate && !!endDate && !!cityIds.length);
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(newTripData);
  };

  return (
    <Flex css={containerStyling}>
      <Box css={boxStyling}>
        <Heading>여행을 기록해 보세요</Heading>
        <form css={formStyling} onSubmit={handleSubmit}>
          <CitySearchBar setCityData={setCityData} />
          <DateInput setDateData={setDateData} />
          <Button variant="primary" disabled={!isAllInputFilled} style={{ width: '400px' }}>
            기록하기
          </Button>
        </form>
      </Box>

      <CreatePageImage aria-label="세계 랜드마크 이미지" css={backgroundImage} />
    </Flex>
  );
};

export default TripCreatePage;
