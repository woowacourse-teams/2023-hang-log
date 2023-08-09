import { END_POINTS } from '@constants/api';
import { userInfo } from '@mocks/data/member';
import type { UserData } from '@type/member';
import { rest } from 'msw';

export const memberHandlers = [
  rest.post(END_POINTS.LOGIN, (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.cookie('refreshToken', 'RjY2NjM5NzA2OWJjuE7c'),
      ctx.json({
        accessToken: 'AYjcyMzY3ZDhiNmJkNTY',
      })
    );
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
