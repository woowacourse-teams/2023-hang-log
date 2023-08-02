import { PATH } from '@constants/path';
import { Button, Toast, useOverlay } from 'hang-log-design-system';
import type { FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import { useCreateTripMutation } from '@hooks/api/useCreateTripMutation';
import { useCityDateForm } from '@hooks/common/useCityDateForm';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import { formStyling } from '@components/trip/TripCreateForm/TripCreateForm.style';

const TripCreateForm = () => {
  const { cityDateInfo, updateCityInfo, updateDateInfo, isCityDateValid } = useCityDateForm();
  const { isOpen: isErrorToastOpen, open: openErrorToast, close: closeErrorToast } = useOverlay();
  const createTripMutation = useCreateTripMutation();
  const navigate = useNavigate();

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    createTripMutation.mutate(cityDateInfo, {
      onSuccess: goToTripEditPageWithId,
      onError: openErrorToast,
    });
  };

  const goToTripEditPageWithId = (id: string) => {
    const targetURL = PATH.EDIT_TRIP(id);
    navigate(targetURL);
  };

  return (
    <>
      <form css={formStyling} onSubmit={handleSubmit}>
        <CitySearchBar updateCityInfo={updateCityInfo} />
        <DateInput updateDateInfo={updateDateInfo} />
        <Button variant="primary" disabled={!isCityDateValid}>
          기록하기
        </Button>
      </form>
      {isErrorToastOpen && (
        <Toast variant="error" closeToast={closeErrorToast}>
          새로운 여행기록을 생성하지 못했습니다. 잠시 후 다시 시도해주세요.
        </Toast>
      )}
    </>
  );
};

export default TripCreateForm;
