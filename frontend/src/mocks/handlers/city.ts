import { END_POINTS } from '@constants/api';
import { cities } from '@mocks/data/city';
import { rest } from 'msw';

export const cityHandlers = [
  rest.get(END_POINTS.CITIES, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(cities));
  }),
];
