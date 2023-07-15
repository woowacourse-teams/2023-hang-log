import { DAY_LOG_ITEM_FILTERS } from '@constants/trip';
import { TripItemData } from '@type/tripItem';

export interface DayLogItemData {
  id: number;
  title: string;
  ordinal: number;
  date: string;
  items: TripItemData[];
}

export type DayLogItemFilters = (typeof DAY_LOG_ITEM_FILTERS)[keyof typeof DAY_LOG_ITEM_FILTERS];
