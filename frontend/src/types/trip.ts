import type { CityData } from '@type/city';
import type { DayLogData } from '@type/dayLog';

export interface TripData {
  id: number;
  title: string;
  sharedStatus: boolean;
  startDate: string;
  endDate: string;
  description: string | null;
  imageUrl: string | null;
  cities: CityData[];
  dayLogs: DayLogData[];
}

export interface TripFormData extends Omit<TripData, 'cities' | 'id' | 'dayLogs' | 'sharedStatus'> {
  cityIds: number[];
}

export interface TripPlaceType {
  id: number;
  name: string;
  coordinate: { lat: number; lng: number };
}

export interface TripSharedStatusData {
  sharedStatus: boolean;
}

export interface TripSharedCodeData {
  sharedCode: string;
}
