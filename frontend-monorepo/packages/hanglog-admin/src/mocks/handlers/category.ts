import { http, HttpResponse } from 'msw';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { categories } from '../data/category';

export const categoryHandlers = [
  http.get(END_POINTS.CATEGORY, () => {
    return HttpResponse.json(categories);
  }),
  http.post(END_POINTS.CATEGORY, async ({ request }) => {
    const newCategoryId = 999;

    return new HttpResponse(null, {
      status: HTTP_STATUS_CODE.CREATED,
      headers: {
        Location: `${END_POINTS.CATEGORY}/${newCategoryId}`,
      },
    });
  }),
  http.put(`${END_POINTS.CATEGORY}/:id`, async ({ params, request }) => {
    const { id } = params;
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.NO_CONTENT });
  }),
];
