export const BASE_URL = '/';

export const END_POINTS = {
  TRIPS: '/trips',
  TRIP: (tripId: number) => `/trips/${tripId}`,
  DAY_LOG: (tripId: number, dayLogId: number) => `/trips/${tripId}/daylogs/${dayLogId}`,
  DAY_LOG_ORDER: (tripId: number, dayLogId: number) => `/trips/${tripId}/daylogs/${dayLogId}/order`,
  CREATE_TRIP_ITEM: (tripId: number) => `/trips/${tripId}/items`,
  CHANGE_TRIP_ITEM: (tripId: number, itemId: number) => `/trips/${tripId}/items/${itemId}`,
} as const;

export const NETWORK = {
  RETRY_COUNT: 3,
} as const;
