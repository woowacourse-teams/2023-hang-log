import { rest } from 'msw';

import { HTTP_STATUS_CODE } from '@constants/api';

export const likeHandlers = [
  rest.post('/trips/:tripId/like', (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.NO_CONTENT));
  }),
];
