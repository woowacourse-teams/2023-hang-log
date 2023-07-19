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

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    //서버에 post 요청 보내기 => 새로운 트립 id 받기
    navigate(PATH.EDIT_TRIP.replace(':id', '1'));
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
