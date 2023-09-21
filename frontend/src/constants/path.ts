export const PATH = {
  ROOT: '/',
  CREATE_TRIP: '/trip-new',
  EDIT_TRIP: (tripId: string) => `/trip/${tripId}/edit`,
  TRIP: (tripId: string) => `/trip/${tripId}`,
  EXPENSE: (tripId: string) => `/trip/${tripId}/expense`,
  SHARE_TRIP: (tripId: string) => `/trip/share/${tripId}`,
  SHARE_EXPENSE: (tripId: string) => `/trip/share/expense/${tripId}`,
  LOGIN: '/login',
  MY_PAGE: '/my-page',
  REDIRECT: '/auth/:provider',
  RELOAD: 0,
} as const;
