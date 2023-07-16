import { trip } from '@mocks/data/trip';
import { rest } from 'msw';

import type { PatchDayLogOrderRequestBody } from '@api/dayLog/patchDayLogItemOrder';

export const dayLogHandlers = [
  rest.patch('/trips/:tripId/daylog/:dayLogId/order', async (req, res, ctx) => {
    const { tripId, dayLogId } = req.params;
    const { itemIds } = await req.json<PatchDayLogOrderRequestBody>();

    return res(ctx.status(200), ctx.json(trip));
  }),
];
