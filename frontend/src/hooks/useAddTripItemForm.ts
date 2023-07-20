import type { TripItemFormType } from '@type/tripItem';
import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import { isEmptyString } from '@utils/validator';

import { useAddTripItemMutation } from '@hooks/api/useAddTripItemMutation';

export const useAddTripItemForm = (
  tripId: number,
  initialDayLogId: number,
  onSuccess?: () => void
) => {
  const addTripItemMutation = useAddTripItemMutation();
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

    addTripItemMutation.mutate({ tripId, ...tripItemInformation }, { onSuccess });
  };

  return { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit };
};
