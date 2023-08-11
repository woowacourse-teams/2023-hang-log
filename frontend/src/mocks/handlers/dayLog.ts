import { HTTP_STATUS_CODE } from '@constants/api';
import { trip } from '@mocks/data/trip';
import { rest } from 'msw';

import type { PatchDayLogTitleRequestBody } from '@api/dayLog/patchDayLogTitle';

export const dayLogHandlers = [
  rest.patch('/trips/:tripId/daylogs/:dayLogId/order', async (req, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.NO_CONTENT));
  }),

  rest.patch('/trips/:tripId/daylogs/:dayLogId', async (req, res, ctx) => {
    const { dayLogId } = req.params;
    const { title } = await req.json<PatchDayLogTitleRequestBody>();

    trip.dayLogs[Number(dayLogId)].title = title;

    return res(ctx.status(HTTP_STATUS_CODE.NO_CONTENT));
  }),
];
