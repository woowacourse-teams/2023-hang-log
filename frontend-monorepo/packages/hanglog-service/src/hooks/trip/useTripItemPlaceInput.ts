import type { ChangeEvent, KeyboardEvent } from 'react';
import { useCallback, useEffect, useRef, useState } from 'react';

import type { TripItemFormData } from '@type/tripItem';

export const useTripItemPlaceInput = (
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void,
  disableError: () => void,
  isUpdatable: boolean
) => {
  const [autoCompleteWidget, setAutoCompleteWidget] =
    useState<google.maps.places.Autocomplete | null>(null);
  const inputRef = useRef<HTMLInputElement | null>(null);

  const handleEnterKeyClick = (event: KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  const handlePlaceChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('title', event.target.value);
    updateInputValue('place', null);

    // 수정할 때만 실행한다
    if (isUpdatable) updateInputValue('isPlaceUpdated', true);
  };

  const handlePlaceSelect = useCallback(() => {
    const place = autoCompleteWidget?.getPlace();

    const name = place?.name;
    const latitude = place?.geometry?.location?.lat();
    const longitude = place?.geometry?.location?.lng();

    if (!name || !latitude || !longitude) return;

    updateInputValue('title', name);
    updateInputValue('place', {
      name,
      latitude,
      longitude,
      apiCategory: place?.types ?? [],
    });
  }, [autoCompleteWidget, updateInputValue]);

  useEffect(() => {
    const autoComplete = new google.maps.places.Autocomplete(inputRef.current!);
    autoComplete.setFields(['name', 'geometry', 'types']);

    setAutoCompleteWidget(autoComplete);
  }, []);

  useEffect(() => {
    if (autoCompleteWidget) {
      autoCompleteWidget.addListener('place_changed', handlePlaceSelect);
    }
  }, [autoCompleteWidget, handlePlaceSelect]);

  return { inputRef, handleEnterKeyClick, handlePlaceChange };
};
