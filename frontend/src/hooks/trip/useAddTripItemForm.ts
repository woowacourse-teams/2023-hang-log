import type { TripItemFormData } from '@type/tripItem';
import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import { isEmptyString } from '@utils/validator';

import { useAddTripItemMutation } from '@hooks/api/useAddTripItemMutation';
import { useUpdateTripItemMutation } from '@hooks/api/useUpdateTripItemMutation';

interface UseAddTripItemFormParams {
  tripId: number;
  initialDayLogId: number;
  itemId?: number;
  initialData?: TripItemFormData;
  onSuccess?: () => void;
}

export const useAddTripItemForm = ({
  tripId,
  initialDayLogId,
  itemId,
  initialData,
  onSuccess,
}: UseAddTripItemFormParams) => {
  const addTripItemMutation = useAddTripItemMutation();
  const updateTripItemMutation = useUpdateTripItemMutation();
  const [tripItemInformation, setTripItemInformation] = useState<TripItemFormData>(
    initialData ?? {
      itemType: true,
      dayLogId: initialDayLogId,
      title: '',
      place: null,
      rating: null,
      expense: null,
      memo: null,
      imageUrls: [],
    }
  );
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

  const isFormError = () => {
    if (isEmptyString(tripItemInformation.title)) {
      return true;
    }

    if (
      tripItemInformation.itemType &&
      !tripItemInformation.place &&
      tripItemInformation.isPlaceUpdated === undefined
    ) {
      return true;
    }

    if (
      tripItemInformation.itemType &&
      !tripItemInformation.place &&
      tripItemInformation.isPlaceUpdated === true
    ) {
      return true;
    }

    return false;
  };

  const disableTitleError = useCallback(() => {
    setIsTitleError(false);
  }, []);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (isFormError()) {
      setIsTitleError(true);

      return;
    }

    if (!itemId) {
      addTripItemMutation.mutate(
        {
          tripId,
          ...tripItemInformation,
          place: tripItemInformation.itemType ? tripItemInformation.place : null,
        },
        { onSuccess }
      );

      return;
    }

    updateTripItemMutation.mutate(
      {
        tripId,
        itemId,
        ...tripItemInformation,
        place: tripItemInformation.itemType ? tripItemInformation.place : null,
      },
      { onSuccess }
    );
  };

  return { tripItemInformation, isTitleError, updateInputValue, disableTitleError, handleSubmit };
};
