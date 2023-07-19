import type { CityData } from '@type/city';

export interface TripsData {
  id: number;
  title: string;
  imageUrl: string | null;
  cities: CityData[];
  startDate: string;
  endDate: string;
  description?: string | null;
}

export interface DateRangeData {
  start: string | null;
  end: string | null;
}
