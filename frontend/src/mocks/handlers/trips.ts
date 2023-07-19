import { tripsMock } from '@mocks/data/trips';
import { rest } from 'msw';

export const tripsHandler = [
  rest.get('/trips', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        trips: tripsMock,
      })
    );
  }),
  rest.get('/trips/none', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        trips: [],
      })
    );
  }),
];
