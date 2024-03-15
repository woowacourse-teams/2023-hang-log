import { useCallback, useState } from 'react';

export const useForm = <T extends Record<string, any>>(
  initialValues: T,
  validate: (values: T) => Record<string, boolean>,
  submitAction: (values: T, onSuccess?: () => void, onError?: () => void) => void,
  { onSuccess, onError }: { onSuccess?: () => void; onError?: () => void }
) => {
  const [formValues, setFormValues] = useState<T>(initialValues);
  const [errors, setErrors] = useState<Record<string, boolean>>({});

  const updateInputValue = useCallback((key: string, value: any) => {
    setFormValues((prev) => ({ ...prev, [key]: value }));
  }, []);

  const disableError = useCallback((errorKey: string) => {
    setErrors((prev) => ({ ...prev, [errorKey]: false }));
  }, []);

  const validateForm = useCallback(() => {
    const validationErrors = validate(formValues);
    setErrors(validationErrors);
    return Object.values(validationErrors).some((isError) => isError);
  }, [formValues, validate]);

  const handleSubmit = useCallback(
    (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault();
      if (!validateForm()) {
        submitAction(formValues, onSuccess, onError);
      }
    },
    [formValues, onSuccess, onError, validateForm, submitAction]
  );

  return { formValues, errors, updateInputValue, disableError, handleSubmit };
};
