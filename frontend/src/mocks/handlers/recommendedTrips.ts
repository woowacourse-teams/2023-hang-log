import { rest } from 'msw';

import { END_POINTS } from '@constants/api';

import { recommendedTripsData } from '@mocks/data/recommendedTripsData';

export const recommendedTripsHandlers = [
  rest.get(END_POINTS.RECOMMENDED_TRIPS, (_, res, ctx) => {
    return res(ctx.status(200), ctx.delay(100), ctx.json(recommendedTripsData));
  }),
];
