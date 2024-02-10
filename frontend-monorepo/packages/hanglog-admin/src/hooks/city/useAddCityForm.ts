import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import { useAddCityMutation } from '../api/useAddCityMutation';
import { useUpdateCityMutation } from '../api/useUpdateCityMutation';

import { isEmptyString, isInvalidLatitude, isInvalidLongitude } from '@utils/validator';

import type { CityFormData } from '@type/city';

interface UseAddCityFormParams {
  cityId?: number;
  initialData?: CityFormData;
  onSuccess?: () => void;
  onError?: () => void;
}

export const UseAddCityForm = ({
  cityId,
  initialData,
  onSuccess,
  onError,
}: UseAddCityFormParams) => {
  const addCityMutation = useAddCityMutation();
  const updateCityMutation = useUpdateCityMutation();

  const [cityInformation, setCityInformation] = useState<CityFormData>(
    initialData ?? {
      name: '',
      country: '',
      latitude: 0,
      longitude: 0,
    }
  );

  const [isNameError, setIsNameError] = useState(false);
  const [isCountryError, setIsCountryError] = useState(false);
  const [isLatitudeError, setIsLatitudeError] = useState(false);
  const [isLongitudeError, setIsLongitudeError] = useState(false);

  const updateInputValue = useCallback(
    <K extends keyof CityFormData>(key: K, value: CityFormData[K]) => {
      setCityInformation((prevCityInformation) => {
        const data = {
          ...prevCityInformation,
          [key]: value,
        };

        return data;
      });
    },
    []
  );

  const disableNameError = useCallback(() => {
    setIsNameError(false);
  }, []);

  const disableCountryError = useCallback(() => {
    setIsCountryError(false);
  }, []);

  const disableLatitudeError = useCallback(() => {
    setIsLatitudeError(false);
  }, []);

  const disableLongitudeError = useCallback(() => {
    setIsLongitudeError(false);
  }, []);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (isEmptyString(cityInformation.name.trim())) {
      setIsNameError(true);
      return;
    }
    if (isEmptyString(cityInformation.country.trim())) {
      setIsCountryError(true);
      return;
    }
    if (isInvalidLatitude(cityInformation.latitude)) {
      setIsLatitudeError(true);
      return;
    }
    if (isInvalidLongitude(cityInformation.longitude)) {
      setIsLongitudeError(true);
      return;
    }

    if (!cityId) {
      addCityMutation.mutate(
        {
          ...cityInformation,
        },
        { onSuccess, onError }
      );

      return;
    }

    updateCityMutation.mutate(
      {
        cityId,
        ...cityInformation,
      },
      { onSuccess, onError }
    );
  };

  return {
    cityInformation,
    isNameError,
    isCountryError,
    isLatitudeError,
    isLongitudeError,
    disableNameError,
    disableCountryError,
    disableLatitudeError,
    disableLongitudeError,
    updateInputValue,
    handleSubmit,
  };
};
