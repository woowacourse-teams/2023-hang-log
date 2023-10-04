import { rest } from 'msw';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { images } from '@mocks/data/image';

export const imageHandlers = [
  rest.post(END_POINTS.IMAGES, (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.CREATED), ctx.json({ imageNames: images }));
  }),
];
