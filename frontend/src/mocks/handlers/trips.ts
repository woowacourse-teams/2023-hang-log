import { END_POINTS } from '@constants/api';
import { trip } from '@mocks/data/trip';
import { tripsMock } from '@mocks/data/trips';
import { rest } from 'msw';

export const tripsHandlers = [
  rest.get('/trips', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        trips: tripsMock,
      })
    );
  }),

  rest.get(`${END_POINTS.TRIPS}/none`, (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        trips: [],
      })
    );
  }),

  rest.get(`${END_POINTS.TRIPS}/:tripId`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(trip));
  }),
];
