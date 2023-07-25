import type { TripItemFormData } from '@type/tripItem';
import type { ChangeEvent, KeyboardEvent } from 'react';
import { useEffect, useRef, useState } from 'react';

export const useTripItemPlaceInput = (
  isError: boolean,
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void,
  disableError: () => void
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
  };

  const handlePlaceSelect = () => {
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
  };

  useEffect(() => {
    // initialize Autocomplete widget
    const autoComplete = new google.maps.places.Autocomplete(inputRef.current!);
    autoComplete.setFields(['name', 'geometry', 'types']);

    setAutoCompleteWidget(autoComplete);
  }, []);

  useEffect(() => {
    if (autoCompleteWidget) {
      // 장소를 선택했을 때의 이벤트 추가
      autoCompleteWidget.addListener('place_changed', handlePlaceSelect);
    }
  }, [autoCompleteWidget]);

  return { inputRef, handleEnterKeyClick, handlePlaceChange };
};
