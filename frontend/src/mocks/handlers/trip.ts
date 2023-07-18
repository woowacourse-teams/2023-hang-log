import { END_POINTS } from '@constants/api';
import { trip } from '@mocks/data/trip';
import { rest } from 'msw';

export const tripHandlers = [
  rest.get(`${END_POINTS.TRIPS}/:tripId`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(trip));
  }),
];
