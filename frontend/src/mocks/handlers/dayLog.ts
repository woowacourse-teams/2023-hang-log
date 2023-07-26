import { trip } from '@mocks/data/trip';
import { rest } from 'msw';

import type { PatchDayLogOrderRequestBody } from '@api/dayLog/patchDayLogItemOrder';
import { PatchDayLogTitleRequestBody } from '@api/dayLog/patchDayLogTitle';

export const dayLogHandlers = [
  rest.patch('/trips/:tripId/daylogs/:dayLogId/order', async (req, res, ctx) => {
    const { tripId, dayLogId } = req.params;
    const { itemIds } = await req.json<PatchDayLogOrderRequestBody>();

    return res(ctx.status(204));
  }),

  rest.patch('/trips/:tripId/daylogs/:dayLogId', async (req, res, ctx) => {
    const { tripId, dayLogId } = req.params;
    const { title } = await req.json<PatchDayLogTitleRequestBody>();

    trip.dayLogs[Number(dayLogId)].title = title;

    return res(ctx.status(204));
  }),
];
