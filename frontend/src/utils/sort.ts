import type { TripsData } from '@type/trips';

export const sortByStartDate = (a: TripsData, b: TripsData) => {
  const dateA = new Date(a.startDate);
  const dateB = new Date(b.startDate);

  return dateA.getTime() - dateB.getTime();
};
