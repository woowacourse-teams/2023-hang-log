import type { CityData } from '@type/city';
import type { DayLogData } from '@type/dayLog';

export interface TripData {
  tripType: TripTypeData;
  id: number;
  title: string;
  sharedCode: string | null;
  isPublished: boolean;
  startDate: string;
  endDate: string;
  description: string | null;
  imageUrl: string | null;
  cities: CityData[];
  dayLogs: DayLogData[];
  writer: {
    nickname: string | null;
    imageUrl: string | null;
  };
  isWriter: boolean | null;
  isLike: boolean | null;
  likeCount: number | null;
  publishedDate: string | null;
}

export type TripTypeData = 'PERSONAL' | 'PUBLISHED' | 'SHARED';

export interface TripFormData
  extends Omit<
    TripData,
    | 'cities'
    | 'id'
    | 'dayLogs'
    | 'sharedCode'
    | 'isPublished'
    | 'writer'
    | 'publishedDate'
    | 'isLike'
    | 'likeCount'
    | 'isWriter'
    | 'tripType'
  > {
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
