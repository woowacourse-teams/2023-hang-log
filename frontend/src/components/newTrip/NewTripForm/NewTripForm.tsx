import { PATH } from '@constants/path';
import { Button } from 'hang-log-design-system';
import type { FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import { useNewTripMutation } from '@hooks/api/useNewTripMutation';
import { useNewTripForm } from '@hooks/newTrip/useNewTripForm';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import { formStyling } from '@components/newTrip/NewTripForm/NewTripForm.style';

const NewTripForm = () => {
  const { newTripData, setCityData, setDateData, isAllInputFilled } = useNewTripForm();
  const newTripMutation = useNewTripMutation();
  const navigate = useNavigate();

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    newTripMutation.mutate(newTripData, {
      onSuccess: goToTripEditPageWithId,
    });
  };

  const goToTripEditPageWithId = (id: string) => {
    const targetURL = PATH.EDIT_TRIP.replace(':tripId', id);
    navigate(targetURL);
  };

  return (
    <form css={formStyling} onSubmit={handleSubmit}>
      <CitySearchBar setCityData={setCityData} />
      <DateInput setDateData={setDateData} />
      <Button variant="primary" disabled={!isAllInputFilled}>
        기록하기
      </Button>
    </form>
  );
};

export default NewTripForm;
