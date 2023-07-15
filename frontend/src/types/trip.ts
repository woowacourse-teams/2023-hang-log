import type { DayLogItemData } from '@type/dayLog';

interface City {
  id: number;
  name: string;
}

export interface TripData {
  id: number;
  title: string;
  startDate: string;
  endDate: string;
  description: string | null;
  imageUrl: string | null;
  cities: City[];
  dayLogs: DayLogItemData[];
}
