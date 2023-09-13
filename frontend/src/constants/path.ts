export const PATH = {
  ROOT: '/',
  MY_TRIPS: '/my-trips',
  CREATE_TRIP: '/trip-new',
  EDIT_TRIP: (tripId: string) => `/trip/${tripId}/edit`,
  TRIP: (tripId: string) => `/trip/${tripId}`,
  EXPENSE: (tripId: string) => `/trip/${tripId}/expense`,
  SHARE_TRIP: (tripId: string) => `/trip/share/${tripId}`,
  SHARE_EXPENSE: (tripId: string) => `/trip/share/expense/${tripId}`,
  COMMUNITY_TRIP: (tripId: string) => `/community/trip/${tripId}`,
  COMMUNITY_EXPENSE: (tripId: string) => `/community/trip/expense/${tripId}`,
  LOGIN: '/login',
  MY_PAGE: '/my-page',
  REDIRECT: '/auth/:provider',
  RELOAD: 0,
} as const;
