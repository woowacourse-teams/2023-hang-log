import { rest } from 'msw';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { cities } from '@mocks/data/city';

export const cityHandlers = [
  rest.get(END_POINTS.CITY, (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.SUCCESS), ctx.json(cities));
  }),
];
