import { END_POINTS } from '@constants/api';
import { city } from '@mocks/data/city';
import { rest } from 'msw';

export const cityHandlers = [
  rest.get(END_POINTS.CITY, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(city));
  }),
];
