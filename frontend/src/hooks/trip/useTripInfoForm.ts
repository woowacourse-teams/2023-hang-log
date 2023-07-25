import type { TripData, TripPutData } from '@type/trip';
import { useEffect, useState } from 'react';

import { isEmptyString } from '@utils/validator';

import { useEditTripMutation } from '@hooks/api/useEditTripMutation';
import { useCityDateForm } from '@hooks/common/useCityDateForm';

export const useTripInfoForm = (information: Omit<TripData, 'dayLogs'>) => {
  const { id: tripId, title, cities, startDate, endDate, description, imageUrl } = information;
  const { cityDateInfo, updateCityInfo, updateDateInfo, isCityDateValid } = useCityDateForm({
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

  const validateInfo = () => {
    if (isCityDateValid && isEmptyString(tripInfo.title)) {
      return false;
    }

    return true;
  };

  const submitEditedInfo = () => {
    if (!validateInfo()) {
      return;
    }

    tripMutation.mutate({
      tripId,
      ...tripInfo,
      startDate: tripInfo.startDate!,
      endDate: tripInfo.endDate!,
    });
  };

  return { tripInfo, updateInputValue, updateCityInfo, updateDateInfo, submitEditedInfo };
};
