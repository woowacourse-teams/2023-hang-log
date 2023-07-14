import { CURRENCY_ICON } from '@constants/trip';

type Currency = keyof typeof CURRENCY_ICON;

interface Place {
  id: number;
  name: string;
  latitude: number;
  longitude: number;
  category: {
    id: number;
    name: string;
  };
}

interface Expense {
  id: number;
  currency: Currency;
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
  rating: number | null;
  memo: string | null;
  place: Place | null;
  expense: Expense | null;
  imageUrls: string[] | null;
}
