import { useCallback, useEffect, useState } from 'react';

import type { CityData } from '@type/city';
import type { DateRangeData, NewTripData } from '@type/trips';

const defaultTripData = {
  startDate: null,
  endDate: null,
  cityIds: [],
};

export const useCityDateForm = (initialTripData?: NewTripData) => {
  const [cityDateInfo, setCityDateInfo] = useState<NewTripData>(initialTripData ?? defaultTripData);
  const [isCityDateValid, setIsCityDateValid] = useState(false);

  useEffect(() => {
    validateInputs();
  }, [cityDateInfo]);

  const updateCityInfo = useCallback((cities: CityData[]) => {
    const cityIds = cities.map((city) => city.id);

    setCityDateInfo((prev) => ({ ...prev, cityIds }));
  }, []);

  const updateDateInfo = useCallback(({ startDate, endDate }: DateRangeData): void => {
    setCityDateInfo((prev) => ({ ...prev, startDate, endDate }));
  }, []);

  const validateInputs = () => {
    const { startDate, endDate, cityIds } = cityDateInfo;

    setIsCityDateValid(!!startDate && !!endDate && !!cityIds.length);
  };

  return { cityDateInfo, updateCityInfo, updateDateInfo, isCityDateValid };
};
