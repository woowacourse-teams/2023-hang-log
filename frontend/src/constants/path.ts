export const PATH = {
  ROOT: '/',
  CREATE_TRIP: '/trip-new',
  EDIT_TRIP: (tripId: number | string) => `/trip/${tripId}/edit`,
  TRIP: (tripId: number | string) => `/trip/${tripId}`,
  EXPENSE: (tripId: number | string) => `/trip/${tripId}/expense`,
  LOGIN: '/login',
  SIGN_UP: '/sign-up',
  MY_PAGE: '/my-page',
  RELOAD: 0,
} as const;
