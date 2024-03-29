import { HttpResponse, http } from 'msw';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { cities } from '@mocks/data/city';

export const cityHandlers = [
  http.get(END_POINTS.CITY, () => {
    return HttpResponse.json(cities);
  }),
  http.post(END_POINTS.CITY, async ({ request }) => {
    const newCityId = 999;

    return new HttpResponse(null, {
      status: HTTP_STATUS_CODE.CREATED,
      headers: {
        Location: `${END_POINTS.CITY}/${newCityId}`,
      },
    });
  }),
  http.put(`${END_POINTS.CITY}/:id`, async ({ params, request }) => {
    const { id } = params;
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.NO_CONTENT });
  }),
];
