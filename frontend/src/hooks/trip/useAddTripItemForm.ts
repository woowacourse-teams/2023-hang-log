import type { TripItemFormData } from '@type/tripItem';
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
  const [tripItemInformation, setTripItemInformation] = useState<TripItemFormData>({
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
    <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => {
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

    if (tripItemInformation.itemType && !tripItemInformation.place) {
      setIsTitleError(true);

      return;
    }

    if (isEmptyString(tripItemInformation.title)) {
      setIsTitleError(true);

      return;
    }

    addTripItemMutation.mutate(
      {
        tripId,
        ...tripItemInformation,
        place: tripItemInformation.itemType ? tripItemInformation.place : null,
      },
      { onSuccess }
    );
  };

  return { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit };
};
