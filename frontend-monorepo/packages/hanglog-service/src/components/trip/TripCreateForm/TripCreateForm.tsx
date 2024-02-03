import type { FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import { Button } from 'hang-log-design-system';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import { formStyling } from '@components/trip/TripCreateForm/TripCreateForm.style';

import { useCreateTripMutation } from '@hooks/api/useCreateTripMutation';
import { useCityDateForm } from '@hooks/common/useCityDateForm';

import { PATH } from '@constants/path';

const TripCreateForm = () => {
  const { cityDateInfo, updateCityInfo, updateDateInfo, isCityDateValid } = useCityDateForm();
  const createTripMutation = useCreateTripMutation();
  const navigate = useNavigate();

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    createTripMutation.mutate(cityDateInfo, {
      onSuccess: goToTripEditPageWithId,
    });
  };

  const goToTripEditPageWithId = (id: string) => {
    const targetURL = PATH.EDIT_TRIP(id);
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

export default TripCreateForm;
