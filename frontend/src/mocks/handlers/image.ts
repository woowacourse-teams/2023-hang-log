import { END_POINTS } from '@constants/api';
import { images } from '@mocks/data/image';
import { rest } from 'msw';

export const imageHandlers = [
  rest.post(END_POINTS.IMAGES, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(images));
  }),
];
