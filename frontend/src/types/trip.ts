import type { CityData } from '@type/city';
import type { DayLogData } from '@type/dayLog';

export interface TripData {
  id: number;
  title: string;
  sharedCode: string | null;
  startDate: string;
  endDate: string;
  description: string | null;
  imageUrl: string | null;
  cities: CityData[];
  dayLogs: DayLogData[];
}

export interface TripFormData extends Omit<TripData, 'cities' | 'id' | 'dayLogs' | 'sharedCode'> {
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

export interface CommunityTripData extends Omit<TripData, 'sharedCode'> {
  writer: {
    nickname: string;
    imageUrl: string;
  };
  isWriter: boolean;
  isLike: boolean;
  likeCount: number;
  publishedDate: string;
}
