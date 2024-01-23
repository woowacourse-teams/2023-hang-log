import { useEffect, useState } from 'react';
import type { ChangeEvent, FormEvent } from 'react';

import { useTripEditMutation } from '@hooks/api/useTripEditMutation';
import { useCityDateForm } from '@hooks/common/useCityDateForm';

import { isEmptyString } from '@utils/validator';

import type { TripData } from '@type/trip';

export const useTripEditForm = (
  {
    id: tripId,
    title,
    cities,
    startDate,
    endDate,
    description,
    imageName,
  }: Omit<
    TripData,
    | 'tripType'
    | 'dayLogs'
    | 'sharedCode'
    | 'isPublished'
    | 'writer'
    | 'publishedDate'
    | 'isLike'
    | 'likeCount'
    | 'isWriter'
    | 'tripType'
  >,
  onClose: () => void
) => {
  const { cityDateInfo, updateCityInfo, updateDateInfo } = useCityDateForm({
    cityIds: cities.map((city) => city.id),
    startDate,
    endDate,
  });
  const [tripInfo, setTripInfo] = useState({ title, description, imageName, ...cityDateInfo });
  const [isCityError, setIsCityError] = useState(false);
  const [isTitleError, setIsTitleError] = useState(false);
  const tripEditMutation = useTripEditMutation();

  useEffect(() => {
    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, ...cityDateInfo };
    });
  }, [cityDateInfo]);

  const updateInputValue =
    (key: keyof TripData) => (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      setTripInfo((prevTripInfo) => {
        return { ...prevTripInfo, [key]: event.target.value };
      });
    };

  const updateCoverImage = (imageName: string | null) => {
    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, imageName };
    });
  };

  const validateCityInput = () => {
    if (cityDateInfo.cityIds.length > 0) {
      setIsCityError(false);

      return true;
    }

    setIsCityError(true);

    return false;
  };

  const validateTitle = () => {
    if (!isEmptyString(tripInfo.title)) {
      setIsTitleError(false);

      return true;
    }

    setIsTitleError(true);

    return false;
  };

  const submitEditedTrip = () => {
    tripEditMutation.mutate(
      {
        ...tripInfo,
        imageName: tripInfo.imageName,
        tripId: String(tripId),
        startDate: tripInfo.startDate!,
        endDate: tripInfo.endDate!,
      },
      {
        onSuccess: onClose,
      }
    );
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();

    const cityInputValidity = validateCityInput();
    const titleValidity = validateTitle();

    if (!cityInputValidity || !titleValidity) return;

    submitEditedTrip();
  };

  return {
    tripInfo,
    isCityInputError: isCityError,
    isTitleError,
    updateInputValue,
    updateCityInfo,
    updateDateInfo,
    updateCoverImage,
    handleSubmit,
  };
};
