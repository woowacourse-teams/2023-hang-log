import type { TripsData } from '@type/trips';

export const sortByStartDate = (a: TripsData, b: TripsData) => {
  const dateA = new Date(a.startDate);
  const dateB = new Date(b.startDate);

  return dateB.getTime() - dateA.getTime();
};

export const sortByNewest = (a: TripsData, b: TripsData) => {
  const tripA = a.id;
  const tripB = b.id;

  return tripB - tripA;
};
