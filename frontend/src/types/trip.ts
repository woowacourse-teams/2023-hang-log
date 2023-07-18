import type { DayLogData } from '@type/dayLog';

interface CityData {
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
  cities: CityData[];
  dayLogs: DayLogData[];
}
