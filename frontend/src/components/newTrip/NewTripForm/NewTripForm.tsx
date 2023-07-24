import { useCityDateForm } from '@/hooks/common/useCityDateForm';
import { PATH } from '@constants/path';
import { Button } from 'hang-log-design-system';
import type { FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import { useNewTripMutation } from '@hooks/api/useNewTripMutation';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import { formStyling } from '@components/newTrip/NewTripForm/NewTripForm.style';

const TripAddForm = () => {
  const { cityDateInfo, updateCityInfo, updateDateInfo, isCityDateValid } = useCityDateForm();
  const newTripMutation = useNewTripMutation();
  const navigate = useNavigate();

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    newTripMutation.mutate(cityDateInfo, {
      onSuccess: goToTripEditPageWithId,
    });
  };

  const goToTripEditPageWithId = (id: string) => {
    const targetURL = PATH.EDIT_TRIP.replace(':tripId', id);
    navigate(targetURL);
  };

  return (
    <form css={formStyling} onSubmit={handleSubmit}>
      <CitySearchBar updateCityInfo={updateCityInfo} />
      <DateInput updateDateInfo={updateDateInfo} />
      <Button variant="primary" disabled={!isCityDateValid}>
        기록하기
      </Button>
    </form>
  );
};

export default TripAddForm;
