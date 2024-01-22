import type { CURRENCY_ICON } from '@constants/trip';

export type CurrencyType = keyof typeof CURRENCY_ICON;

export type StarRatingData = 0 | 0.5 | 1 | 1.5 | 2 | 2.5 | 3 | 3.5 | 4 | 4.5 | 5;

interface PlaceData {
  id: number;
  name: string;
  latitude: number;
  longitude: number;
  category: {
    id: number;
    name: string;
  };
}

interface ExpenseData {
  id: number;
  currency: CurrencyType;
  amount: number;
  category: {
    id: number;
    name: string;
  };
}

export interface TripItemData {
  id: number;
  itemType: boolean;
  title: string;
  ordinal: number;
  rating: StarRatingData | null;
  memo: string | null;
  place: PlaceData | null;
  expense: ExpenseData | null;
  imageNames: string[];
}

export type TripItemCategory = '장소' | '기타';

export interface TripItemFormData {
  itemType: boolean;
  dayLogId: number | null;
  title: string;
  isPlaceUpdated?: boolean;
  place: {
    name: string;
    latitude: number;
    longitude: number;
    apiCategory: string[];
  } | null;
  rating: StarRatingData | null;
  expense: {
    currency: string;
    amount: number;
    categoryId: number;
  } | null;
  memo: string | null;
  imageNames: string[];
}
