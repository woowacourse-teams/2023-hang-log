import { rest } from 'msw';

import type { UserData } from '@type/member';

import { END_POINTS } from '@constants/api';

import { accessToken, refreshToken, userInfo } from '@mocks/data/member';

export const memberHandlers = [
  rest.post(END_POINTS.LOGIN(':provider'), (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.cookie('refreshToken', refreshToken),
      ctx.json({ accessToken })
    );
  }),

  rest.post(END_POINTS.LOGOUT, (_, res, ctx) => {
    return res(ctx.status(204));
  }),

  rest.get(END_POINTS.MY_PAGE, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(userInfo));
  }),

  rest.put(END_POINTS.MY_PAGE, async (req, res, ctx) => {
    const { nickname } = await req.json<UserData>();

    userInfo.nickname = nickname;

    return res(ctx.status(204));
  }),
];
