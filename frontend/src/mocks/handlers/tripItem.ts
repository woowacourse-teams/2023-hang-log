import { trip } from '@mocks/data/trip';
import type { CurrencyType, TripItemFormData } from '@type/tripItem';
import { rest } from 'msw';

export const tripItemHandlers = [
  rest.post('/trips/:tripId/items', async (req, res, ctx) => {
    const { tripId } = req.params;
    const response = await req.json<TripItemFormData>();

    const newTripItem = {
      id: 7,
      itemType: false,
      title: response.title,
      ordinal: 1,
      rating: response.rating,
      memo: response.memo,
      place: response.place
        ? {
            ...response.place,
            id: 123,
            category: {
              id: 3,
              name: '명소',
            },
          }
        : null,
      expense: response.expense
        ? {
            id: 4,
            currency: response.expense.currency as CurrencyType,
            amount: response.expense.amount,
            category: {
              id: 500,
              name: '교통',
            },
          }
        : null,
      imageUrls: [],
    };

    trip.dayLogs[2].items.push(newTripItem);

    return res(ctx.status(200));
  }),
];
