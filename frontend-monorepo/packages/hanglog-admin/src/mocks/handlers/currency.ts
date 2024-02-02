import { http, HttpResponse } from 'msw';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { currencyListData } from '../data/currency';

export const currencyHandlers = [
  http.get(`${END_POINTS.CURRENCY}`, ({ request }) => {
    const url = new URL(request.url);
    const page = Number(url.searchParams.getAll('page'));
    const size = Number(url.searchParams.getAll('size'));

    return HttpResponse.json(currencyListData(page, size));
  }),
  http.post(END_POINTS.CURRENCY, async ({ request }) => {
    const newCurrencyId = 999;

    return new HttpResponse(null, {
      status: HTTP_STATUS_CODE.CREATED,
      headers: {
        Location: `${END_POINTS.CURRENCY}/${newCurrencyId}`,
      },
    });
  }),
  http.put(`${END_POINTS.CURRENCY}/:id`, async ({ params, request }) => {
    const { id } = params;
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.NO_CONTENT });
  }),
];
