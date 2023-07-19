import { postNewTrip } from '@/api/trips/trips';
import { PATH } from '@/constants/path';
import { Button } from 'hang-log-design-system';
import type { FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import { useNewTripForm } from '@hooks/newTrip/useNewTripForm';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';
import DateInput from '@components/common/DateInput/DateInput';
import { formStyling } from '@components/newTrip/NewTripForm/NewTripForm.style';

const NewTripForm = () => {
  const { newTripData, setCityData, setDateData, isAllInputFilled } = useNewTripForm();
  const navigate = useNavigate();

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const id = await postNewTrip(newTripData);
    console.log(id);
    navigate(PATH.EDIT_TRIP.replace(':id', id));
  };

  return (
    <form css={formStyling} onSubmit={handleSubmit}>
      <CitySearchBar setCityData={setCityData} />
      <DateInput setDateData={setDateData} />
      <Button variant="primary" disabled={!isAllInputFilled} style={{ width: '400px' }}>
        기록하기
      </Button>
    </form>
  );
};

export default NewTripForm;
