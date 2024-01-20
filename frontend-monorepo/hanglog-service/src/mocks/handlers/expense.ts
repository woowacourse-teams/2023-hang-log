import { rest } from 'msw';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { expense, expenseCategories } from '@mocks/data/expense';

export const expenseHandlers = [
  rest.get(END_POINTS.EXPENSE(':tripId'), (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.SUCCESS), ctx.json(expense));
  }),

  rest.get(END_POINTS.EXPENSE_CATEGORY, (_, res, ctx) => {
    return res(ctx.status(HTTP_STATUS_CODE.SUCCESS), ctx.json(expenseCategories));
  }),

  rest.get(`${END_POINTS.SHARED_EXPENSE(':tripId')}`, async (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(HTTP_STATUS_CODE.SUCCESS), ctx.json(expense));
  }),

  rest.get(`${END_POINTS.COMMUNITY_EXPENSE(':tripId')}`, async (_, res, ctx) => {
    return res(ctx.delay(1000), ctx.status(HTTP_STATUS_CODE.SUCCESS), ctx.json(expense));
  }),
];
