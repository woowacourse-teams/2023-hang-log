export const PATH = {
  ROOT: '/',
  CREATE_TRIP: '/trip-new',
  EDIT_TRIP: (tripId: number | string) => `/trip/${tripId}/edit`,
  TRIP: (tripId: number | string) => `/trip/${tripId}`,
  EXPENSE: (tripId: number | string) => `/trip/${tripId}/expense`,
  SHARED: (code: string) => `/trip/share/${code}`,
  MY_PAGE: '/users/:userId',
  RELOAD: 0,
} as const;
