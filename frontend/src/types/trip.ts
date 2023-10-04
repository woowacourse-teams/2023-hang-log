import type { CityData } from '@type/city';
import type { DayLogData } from '@type/dayLog';

export interface TripData {
  id: number;
  title: string;
  sharedCode: string | null;
  isPublished: boolean;
  startDate: string;
  endDate: string;
  description: string | null;
  imageName: string | null;
  cities: CityData[];
  dayLogs: DayLogData[];
}

export interface TripFormData
  extends Omit<TripData, 'cities' | 'id' | 'dayLogs' | 'sharedCode' | 'isPublished'> {
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

export interface CommunityTripData extends Omit<TripData, 'sharedCode' | 'isPublished'> {
  writer: {
    nickname: string;
    imageUrl: string;
  };
  isWriter: boolean;
  isLike: boolean;
  likeCount: number;
  publishedDate: string;
}
