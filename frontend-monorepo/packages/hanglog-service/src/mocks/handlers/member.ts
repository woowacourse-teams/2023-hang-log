import { rest } from 'msw';

import type { UserData } from '@type/member';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { accessToken, refreshToken, userInfo } from '@mocks/data/member';

export const memberHandlers = [
  rest.post(END_POINTS.LOGIN(':provider'), (_, res, ctx) => {
    return res(
      ctx.status(HTTP_STATUS_CODE.SUCCESS),
      ctx.cookie('refreshToken', refreshToken),
      ctx.json({ accessToken })
    );
  }),

  rest.delete(END_POINTS.LOGOUT, (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.NO_CONTENT));
  }),

  rest.get(END_POINTS.MY_PAGE, (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.SUCCESS), ctx.json(userInfo));
  }),

  rest.put(END_POINTS.MY_PAGE, async (req, res, ctx) => {
    const { nickname } = await req.json<UserData>();

    userInfo.nickname = nickname;

    return res(ctx.status(HTTP_STATUS_CODE.NO_CONTENT));
  }),

  rest.delete(END_POINTS.ACCOUNT, (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.NO_CONTENT));
  }),
];
