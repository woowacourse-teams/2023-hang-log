import type { FormEvent } from 'react';
import { useCallback, useState } from 'react';

import type { CityFormData } from '@type/city';

import { isEmptyString, isInvalidLatitude, isInvalidLongitude } from '@utils/validator';

import { useAddCityMutation } from './useAddCityMutation';
import { useUpdateCityMutation } from './useUpdateCityMutation';

interface useAddCityFormParams {
  cityId?: number;
  initialData?: CityFormData;
  onSuccess?: () => void;
  onError?: () => void;
}

export const useAddCityForm = (
  { cityId, initialData, onSuccess, onError }: useAddCityFormParams
) => {
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

  const [errors, setErrors] = useState({
    isNameError: false,
    isCountryError: false,
    isLatitudeError: false,
    isLongitudeError: false,
  });

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

  const disableError = useCallback((errorKey: keyof typeof errors) => {
    setErrors((prev) => ({ ...prev, [errorKey]: false }));
  }, []);

  const validateForm = () => {
    const { name, country, latitude, longitude } = cityInformation;
    const newErrors = {
      isNameError: isEmptyString(name.trim()),
      isCountryError: isEmptyString(country.trim()),
      isLatitudeError: isInvalidLatitude(latitude),
      isLongitudeError: isInvalidLongitude(longitude),
    };

    setErrors(newErrors);

    return Object.values(newErrors).some((isError) => isError);
  };

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (validateForm()) {
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
    errors,
    disableError,
    updateInputValue,
    handleSubmit,
  };
};
