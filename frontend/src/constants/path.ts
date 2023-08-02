export const PATH = {
  ROOT: '/',
  CREATE_TRIP: '/trip-new',
  EDIT_TRIP: (tripId: number | string) => `/trip/${tripId}/edit`,
  TRIP: (tripId: number | string) => `/trip/${tripId}`,
  EXPENSE: '/trip/:tripId/expense',
  RELOAD: 0,
} as const;
