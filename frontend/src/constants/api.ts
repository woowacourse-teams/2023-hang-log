const PROD = process.env.NODE_ENV === 'production';

export const BASE_URL = PROD
  ? `${window.location.protocol}//${process.env.PROD_BASE_URL}/api`
  : '/';

export const END_POINTS = {
  TRIPS: '/trips',
  TRIP: (tripId: number) => `/trips/${tripId}`,
  DAY_LOG: (tripId: number, dayLogId: number) => `/trips/${tripId}/daylogs/${dayLogId}`,
  DAY_LOG_ORDER: (tripId: number, dayLogId: number) => `/trips/${tripId}/daylogs/${dayLogId}/order`,
  CREATE_TRIP_ITEM: (tripId: number) => `/trips/${tripId}/items`,
  CHANGE_TRIP_ITEM: (tripId: number, itemId: number) => `/trips/${tripId}/items/${itemId}`,
  CITIES: '/cities',
  EXPENSE_CATEGORY: '/expense-category',
} as const;

export const NETWORK = {
  RETRY_COUNT: 3,
} as const;

export const HTTP_STATUS_CODE = {
  BAD_REQUEST: 400,
  NOT_FOUND: 404,
  INTERNAL_SERVER_ERROR: 500,
} as const;

export const HTTP_ERROR_MESSAGE = {
  [HTTP_STATUS_CODE.NOT_FOUND]: {
    HEADING: '길을 잃었나요?',
    BODY: '요청하신 페이지를 찾을 수 없습니다.',
    BUTTON: '홈으로 가기',
  },
  [HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR]: {
    HEADING: '현재 페이지를 표시할 수 없습니다.',
    BODY: `잠시 후 다시 시도해주세요.`,
    BUTTON: '새로고침',
  },
  [HTTP_STATUS_CODE.BAD_REQUEST]: {
    HEADING: '잘못된 요청입니다.',
    BODY: '확인 후 다시 시도해주세요.',
    BUTTON: '홈으로 가기',
  },
} as const;

export const ERROR_MESSAGE = '오류가 발생했습니다. 잠시 후 다시 시도해주세요.';
