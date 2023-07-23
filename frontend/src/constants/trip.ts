export const DEFAULT_CURRENCY = 'KRW';

export const CURRENCY_ICON = {
  KRW: '₩',
  USD: '$',
  EUR: '€',
  GBP: '£',
  JPY: '¥',
  VND: '₫',
  CNY: '¥',
  CHF: '₣',
  SGD: '$',
  PHP: '₱',
  THB: '฿',
  HKD: '$',
} as const;

export const DAY_LOG_ITEM_FILTERS = {
  ALL: '전체',
  SPOT: '장소',
} as const;

export const TRIP_ITEM_ADD_CATEGORIES = {
  SPOT: '장소',
  NON_SPOT: '기타',
} as const;
