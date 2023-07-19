import { TripItemFormType } from '@type/tripItem';
import { FormEvent, useCallback, useState } from 'react';

import { isEmptyString } from '@utils/validator';

import { useAddTripItemMutation } from './api/useAddTripItemMutation';

export const useAddTripItemForm = (
  tripId: number,
  initialDayLogId: number,
  onSuccess?: CallableFunction
) => {
  const addTripItemMutation = useAddTripItemMutation(onSuccess);
  const [tripItemInformation, setTripItemInformation] = useState<TripItemFormType>({
    itemType: true,
    dayLogId: initialDayLogId,
    title: '',
    place: null,
    rating: null,
    expense: null,
    memo: null,
    imageUrls: [],
  });
  const [isTitleError, setIsTitleError] = useState(false);

  const updateInputValue = useCallback(
    <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => {
      setTripItemInformation((prevTripItemInformation) => {
        const data = {
          ...prevTripItemInformation,
          [key]: value,
        };

        return data;
      });
    },
    []
  );

  const disableTitleError = useCallback(() => {
    setIsTitleError(false);
  }, []);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (isEmptyString(tripItemInformation.title)) {
      setIsTitleError(true);

      return;
    }

    addTripItemMutation.mutate({ tripId, ...tripItemInformation });
  };

  return { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit };
};
