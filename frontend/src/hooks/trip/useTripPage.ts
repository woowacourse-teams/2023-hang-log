import { useMemo } from 'react';

import { useSelect } from 'hang-log-design-system';

import { useTrip } from './useTrip';

export const useTripPage = (tripId: string) => {
  const { tripData, dates } = useTrip(tripId);

  const { selected: selectedDayLogId, handleSelectClick: handleDayLogIdSelectClick } = useSelect(
    tripData.dayLogs[0].id
  );

  const selectedDayLog = tripData.dayLogs.find((log) => log.id === selectedDayLogId)!;

  const places = useMemo(
    () =>
      selectedDayLog.items
        .filter((item) => item.itemType)
        .map((item) => ({
          id: item.id,
          name: item.title,
          coordinate: { lat: item.place!.latitude, lng: item.place!.longitude },
        })),
    [selectedDayLog.items]
  );

  return { handleDayLogIdSelectClick, selectedDayLog, places, dates };
};
