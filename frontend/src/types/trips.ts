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
  startDate: string | null;
  endDate: string | null;
}

export interface NewTripData {
  startDate: string | null;
  endDate: string | null;
  cityIds: number[];
}

export interface RecommendedTripsData {
  title: string;
  trips: (TripsData & {
    authorNickname: string;
    description: string;
    likeCount: number;
    isLike: boolean;
  })[];
}

export interface CommunityTripsData {
  lastPageIndex: number;
  trips: (TripsData & {
    authorNickname: string;
    description: string;
    likeCount: number;
    isLike: boolean;
  })[];
}
