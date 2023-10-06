import { rest } from 'msw';

import { communityTripsData } from '@mocks/data/communityTripsData';

export const communityTripsHandlers = [
  rest.get('/community/trips', (_, res, ctx) => {
    return res(ctx.status(200), ctx.delay(100), ctx.json(communityTripsData));
  }),
];
