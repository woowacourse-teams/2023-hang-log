import { trip } from '@mocks/data/trip';
import { rest } from 'msw';

import type { PatchDayLogOrderRequestBody } from '@api/dayLog/patchDayLogItemOrder';

export const dayLogHandlers = [
  rest.patch('/trips/:tripId/daylogs/:dayLogId/order', async (req, res, ctx) => {
    const { tripId, dayLogId } = req.params;
    const { itemIds } = await req.json<PatchDayLogOrderRequestBody>();

    trip.dayLogs[0].items = [
      trip.dayLogs[0].items[1],
      trip.dayLogs[0].items[0],
      trip.dayLogs[0].items[2],
    ];

    return res(ctx.status(200));
  }),
];
