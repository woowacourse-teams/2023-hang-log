export const PROD = process.env.NODE_ENV === 'production';

export const BASE_URL = PROD
  ? `${window.location.protocol}//${process.env.PROD_BASE_URL}`
  : 'http://localhost:3000';

export const AXIOS_BASE_URL = PROD
  ? `${window.location.protocol}//${process.env.PROD_BASE_URL}/api`
  : '/';

export const END_POINTS = {
  TRIPS: '/trips',
  TRIP: (tripId: number) => `/trips/${tripId}`,
  DAY_LOG: (tripId: number, dayLogId: number) => `/trips/${tripId}/daylogs/${dayLogId}`,
  DAY_LOG_ORDER: (tripId: number, dayLogId: number) => `/trips/${tripId}/daylogs/${dayLogId}/order`,
  CREATE_TRIP_ITEM: (tripId: number) => `/trips/${tripId}/items`,
  CHANGE_TRIP_ITEM: (tripId: number, itemId: number) => `/trips/${tripId}/items/${itemId}`,
  CITY: '/cities',
  EXPENSE_CATEGORY: '/categories',
  EXPENSE: (tripId: number | string) => `/trips/${tripId}/expense`,
  IMAGES: '/images',
  SHARE: (tripId: number | string) => `/trips/${tripId}/share`,
  TOKEN: '/token',
  LOGIN: (provider: string) => `/login/${provider}`,
  LOGOUT: '/logout',
  MY_PAGE: '/mypage',
  ACCOUNT: '/account',
  SHARED_PAGE: (code: string) => `/shared-trips/${code}`,
} as const;

export const NETWORK = {
  RETRY_COUNT: 3,
  TIMEOUT: 10000,
} as const;

export const HTTP_STATUS_CODE = {
  SUCCESS: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  NOT_FOUND: 404,
  INTERNAL_SERVER_ERROR: 500,
} as const;

export const ERROR_CODE = {
  TOKEN_ERROR_RANGE: 9000,
  INVALID_REFRESH_TOKEN: 9101,
  INVALID_ACCESS_TOKEN: 9102,
  EXPIRED_REFRESH_TOKEN: 9103,
  EXPIRED_ACCESS_TOKEN: 9104,
  INVALID_TOKEN_VALIDATE: 9105,
  NULL_REFRESH_TOKEN: 9106,
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

export const ACCESS_TOKEN_KEY = 'ACCESS_TOKEN';

export const KAKAO_AUTH_API_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.KAKAO_API_KEY}&redirect_uri=${BASE_URL}/${process.env.KAKAO_REDIRECT_URI}&response_type=code`;

export const GOOGLE_AUTH_API_URL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.GOOGLE_CLIENT_ID}&response_type=code&redirect_uri=${BASE_URL}/${process.env.GOOGLE_REDIRECT_URI}&scope=https%3A//www.googleapis.com/auth/userinfo.profile`;
