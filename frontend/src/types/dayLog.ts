import { DAY_LOG_ITEM_FILTERS } from '@constants/trip';
import type { TripItemData } from '@type/tripItem';

export interface DayLogData {
  id: number;
  title: string;
  ordinal: number;
  date: string;
  items: TripItemData[];
}

export type DayLogFiltersType = (typeof DAY_LOG_ITEM_FILTERS)[keyof typeof DAY_LOG_ITEM_FILTERS];
