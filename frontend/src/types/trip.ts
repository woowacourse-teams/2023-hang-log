import type { CityData } from '@type/city';
import type { DayLogData } from '@type/dayLog';

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
