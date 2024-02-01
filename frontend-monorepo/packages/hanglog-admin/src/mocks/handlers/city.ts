import { http, HttpResponse } from 'msw';

import { END_POINTS } from '@constants/api';

import { cities } from '@mocks/data/city';

export const cityHandlers = [
  http.get(END_POINTS.CITY, () => {
    return HttpResponse.json(cities);
  }),
];
