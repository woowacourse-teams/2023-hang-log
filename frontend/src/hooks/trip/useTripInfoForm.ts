import { useCityDateForm } from '@/hooks/common/useCityDateForm';
import { getDayLengthFromDateRange } from '@/utils/calculator';
import { isEmptyString } from '@/utils/validator';
import type { TripData, TripPutData } from '@type/trip';
import { useEffect, useState } from 'react';

import { useEditTripMutation } from '@hooks/api/useEditTripMutation';

export const useTripInfoForm = (information: Omit<TripData, 'dayLogs'>) => {
  const { id, title, cities, startDate, endDate, description, imageUrl } = information;
  const { cityDateInfo, updateCityInfo, updateDateInfo, isCityDateValid } = useCityDateForm({
    cityIds: cities.map((city) => city.id),
    startDate,
    endDate,
  });
  const [tripInfo, setTripInfo] = useState({ title, description, imageUrl, ...cityDateInfo });

  const tripMutation = useEditTripMutation();
  const originalDayLength = getDayLengthFromDateRange(startDate, endDate);

  useEffect(() => {
    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, ...cityDateInfo };
    });
  }, [cityDateInfo]);

  const updateInputValue = <K extends keyof TripPutData>(key: K, value: TripPutData[K]) => {
    setTripInfo((prevTripInfo) => {
      return { ...prevTripInfo, [key]: value };
    });
  };

  const submitEditedInfo = () => {
    if (isCityDateValid && isEmptyString(tripInfo.title)) {
      return;
    }

    const changedDayLength = getDayLengthFromDateRange(tripInfo.startDate, tripInfo.endDate);

    if (changedDayLength < originalDayLength) {
      confirm(
        '기존 입력한 날짜보다 기간이 짧습니다. \n 줄어든만큼 입력한 여행정보가 삭제됩니다. 그래도 변경하시겠습니까?'
      );
    }

    console.log(tripInfo);

    tripMutation.mutate({
      tripId: id,
      ...tripInfo,
      startDate: tripInfo.startDate!,
      endDate: tripInfo.endDate!,
    });
  };

  return { tripInfo, updateInputValue, updateCityInfo, updateDateInfo, submitEditedInfo };
};
