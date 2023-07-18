import { TripsType } from '@type/trips';

export const sortByStartDate = (a: TripsType, b: TripsType) => {
  const dateA = new Date(a.startDate);
  const dateB = new Date(b.startDate);

  return dateA.getTime() - dateB.getTime();
};
