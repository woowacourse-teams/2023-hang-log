import type { CityData } from '@type/city';
import type { DateRangeData, NewTripData } from '@type/trips';
import { useEffect, useState } from 'react';

const initialNewTripData = {
  startDate: null,
  endDate: null,
  cityIds: [],
};

export const useNewTripForm = (newTrip?: NewTripData) => {
  const [newTripData, setNewTripData] = useState<NewTripData>(newTrip ?? initialNewTripData);
  const [isAllInputFilled, setIsAllInputFilled] = useState(false);

  useEffect(() => {
    validateInputs();
  }, [newTripData]);

  const setCityData = (cities: CityData[]) => {
    const cityIds = cities.map((city) => city.id);

    setNewTripData((prev) => ({ ...prev, cityIds }));
  };

  const setDateData = (dateRange: DateRangeData) => {
    const { start: startDate, end: endDate } = dateRange;

    setNewTripData((prev) => ({ ...prev, startDate, endDate }));
  };

  const validateInputs = () => {
    const { startDate, endDate, cityIds } = newTripData;

    setIsAllInputFilled(!!startDate && !!endDate && !!cityIds.length);
  };

  return { newTripData, setCityData, setDateData, isAllInputFilled };
};
