import { END_POINTS } from '@constants/api';
import { trip } from '@mocks/data/trip';
import { rest } from 'msw';

interface PatchDayLogRequestBody {
  itemIds: number[];
}

export const dayLogHandlers = [
  rest.patch('/trips/:tripId/daylog/:dayLogId/order', async (req, res, ctx) => {
    const { tripId, dayLogId } = req.params;
    const { itemIds } = await req.json<PatchDayLogRequestBody>();

    console.log(itemIds);

    return res(ctx.status(200), ctx.json(trip));
  }),
];
