import type { CityFormData } from '@type/city';

import { useForm } from '@hooks/useForm';

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

  const initialValues: CityFormData = initialData ?? {
    name: '',
    country: '',
    latitude: 0,
    longitude: 0,
  };

  const validate = (values: CityFormData) => {
    return {
      name: isEmptyString(values.name.trim()),
      country: isEmptyString(values.country.trim()),
      latitude: isInvalidLatitude(values.latitude),
      longitude: isInvalidLongitude(values.longitude),
    };
  };

  const submitAction = (values: CityFormData, onSuccess?: () => void, onError?: () => void) => {
    if (cityId !== undefined) {
      updateCityMutation.mutate({ ...values, cityId }, { onSuccess, onError });
    } else {
      addCityMutation.mutate(values, { onSuccess, onError });
    }
  };

  const { formValues, errors, updateInputValue, disableError, handleSubmit } = useForm(
    initialValues,
    validate,
    submitAction,
    { onSuccess, onError }
  );

  const adjustedErrors = {
    isNameError: errors.name,
    isCountryError: errors.country,
    isLatitudeError: errors.latitude,
    isLongitudeError: errors.longitude,
  };

  return {
    cityInformation: formValues,
    errors: adjustedErrors,
    disableError,
    updateInputValue,
    handleSubmit,
  };
};
