import { END_POINTS } from '@constants/api';
import { trip } from '@mocks/data/trip';
import { trips } from '@mocks/data/trips';
import { rest } from 'msw';

export const tripsHandlers = [
  rest.get('/trips', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(trips));
  }),
  rest.get('/trips/none', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json([]));
  }),
  rest.post('/trips', (_, res, ctx) => {
    return res(ctx.status(201), ctx.set('Location', '1'));
  }),

  rest.get(`${END_POINTS.TRIPS}/:tripId`, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(trip));
  }),
];
