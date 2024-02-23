import { HttpResponse, http } from 'msw';

import { END_POINTS, HTTP_STATUS_CODE } from '@constants/api';

import { adminMembers } from '../data/adminMember';

export const adminMemberHandlers = [
  http.get(END_POINTS.MEMBER, () => {
    return HttpResponse.json(adminMembers);
  }),
  http.post(END_POINTS.MEMBER, async ({ request }) => {
    const newAdminMemberId = 999;

    return new HttpResponse(null, {
      status: HTTP_STATUS_CODE.CREATED,
      headers: {
        Location: `${END_POINTS.MEMBER}/${newAdminMemberId}`,
      },
    });
  }),
  http.patch(`${END_POINTS.MEMBER}/:id/password`, async ({ params, request }) => {
    const { id } = params;
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.NO_CONTENT });
  }),
];
