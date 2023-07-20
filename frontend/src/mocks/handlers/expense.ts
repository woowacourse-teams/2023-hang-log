import { END_POINTS } from '@constants/api';
import { expenseCategories } from '@mocks/data/expense';
import { rest } from 'msw';

export const expenseHandlers = [
  rest.get(END_POINTS.EXPENSE_CATEGORY, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(expenseCategories));
  }),
];
