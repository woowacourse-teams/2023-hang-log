import { createTripMutation } from '@/hooks/api/useTripEditMutation';
import type { TripData, TripFormData } from '@type/trip';
import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';

import { useCityDateForm } from '@hooks/common/useCityDateForm';

export const useTripInfoForm = (information: Omit<TripData, 'dayLogs'>, onClose: () => void) => {
  const { id: tripId, title, cities, startDate, endDate, description, imageUrl } = information;
  const { cityDateInfo, updateCityInfo, updateDateInfo } = useCityDateForm({
    cityIds: cities.map((city) => city.id),
    startDate,
    endDate,
  });
  const [tripInfo, setTripInfo] = useState({ title, description, imageUrl, ...cityDateInfo });
  const [isCityInputError, setCityInputError] = useState(false);
  const tripEditMutation = createTripMutation();

  useEffect(() => {
    validateCityInput();

    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, ...cityDateInfo };
    });
  }, [cityDateInfo]);

  const updateInputValue = <K extends keyof TripFormData>(key: K, value: TripFormData[K]) => {
    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, [key]: value };
    });
  };

  const validateCityInput = () => {
    if (cityDateInfo.cityIds.length > 0) {
      setCityInputError(false);

      return;
    }

    setCityInputError(true);
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();

    if (isCityInputError) return;

    tripEditMutation.mutate({
      tripId,
      ...tripInfo,
      startDate: tripInfo.startDate!,
      endDate: tripInfo.endDate!,
    });

    onClose();
  };

  return {
    tripInfo,
    isCityInputError,
    updateInputValue,
    updateCityInfo,
    updateDateInfo,
    handleSubmit,
  };
};
