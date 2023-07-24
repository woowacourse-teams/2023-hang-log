import type { TripData, TripPutData } from '@type/trip';
import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';

import { useNewTripForm } from './useNewTripForm';

export const useEditTripInfo = (information: Omit<TripData, 'dayLogs'>) => {
  const { id, title, cities, startDate, endDate, description, imageUrl } = information;
  const {
    newTripData: cityDateInfo,
    setCityData,
    setDateData,
    isAllInputFilled: isCityDateValid,
  } = useNewTripForm({
    cityIds: cities.map((city) => city.id),
    startDate,
    endDate,
  });
  const [tripInfo, setTripInfo] = useState({ title, description, imageUrl, ...cityDateInfo });
  const [isInputError, setIsInputError] = useState(false);

  useEffect(() => {
    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, ...cityDateInfo };
    });
  }, [cityDateInfo]);

  const updateInputValue = <K extends keyof TripPutData>(key: K, value: TripPutData[K]) => {
    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, [key]: value };
    });
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();

    if (!isCityDateValid || !tripInfo.title) {
      setIsInputError(true);
      console.log('error');
      return;
    }

    //서버로 보내기
    console.log(tripInfo);
  };

  return { tripInfo, updateInputValue, setCityData, setDateData, handleSubmit };
};
