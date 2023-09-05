export const PATH = {
  ROOT: '/',
  CREATE_TRIP: '/trip-new',
  EDIT_TRIP: (tripId: number | string) => `/trip/${tripId}/edit`,
  TRIP: (tripId: number | string) => `/trip/${tripId}`,
  EXPENSE: (tripId: number | string) => `/trip/${tripId}/expense`,
  SHARE: (shareCode: string | number) => `/trip/share/${shareCode}`,
  SHARE_EXPENSE: (tripId: number | string) => `/trip/share/expense/${tripId}`,
  LOGIN: '/login',
  MY_PAGE: '/my-page',
  REDIRECT: '/auth/:provider',
  RELOAD: 0,
} as const;
