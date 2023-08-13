export const PATH = {
  ROOT: '/',
  CREATE_TRIP: '/trip-new',
  EDIT_TRIP: (tripId: number | string) => `/trip/${tripId}/edit`,
  TRIP: (tripId: number | string) => `/trip/${tripId}`,
  EXPENSE: (tripId: number | string) => `/trip/${tripId}/expense`,
  SHARE: (shareCode: string) => `/trip/share/${shareCode}`,
  LOGIN: '/login',
  SIGN_UP: '/sign-up',
  MY_PAGE: '/my-page',
  REDIRECT: '/auth/:provider',
  RELOAD: 0,
} as const;
