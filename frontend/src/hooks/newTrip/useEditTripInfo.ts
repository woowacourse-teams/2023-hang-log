import type { TripData, TripPutData } from '@type/trip';
import { useEffect, useState } from 'react';

import { useEditTripMutation } from '@hooks/api/useEditTripMutation';
import { useNewTripForm } from '@hooks/newTrip/useNewTripForm';

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
  const tripMutation = useEditTripMutation();

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

  const putEditedInfo = () => {
    if (isCityDateValid && !!tripInfo.title) {
      return;
    }

    tripMutation.mutate({
      tripId: id,
      ...tripInfo,
      startDate: tripInfo.startDate!,
      endDate: tripInfo.endDate!,
    });
  };

  return { tripInfo, updateInputValue, setCityData, setDateData, putEditedInfo };
};
