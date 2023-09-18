import { HTTPError } from '@api/HTTPError';
import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS, ERROR_CODE, HTTP_STATUS_CODE } from '@constants/api';

interface postLikeParams {
  id: number;
  isLike: boolean;
  isLoggedIn: boolean;
}

export const postLike = ({ id, isLike, isLoggedIn }: postLikeParams) => {
  if (!isLoggedIn)
    throw new HTTPError(
      HTTP_STATUS_CODE.UNAUTHORIZED,
      '로그인이 필요합니다.',
      ERROR_CODE.UNAUTHORIZED
    );

  return axiosInstance.post(END_POINTS.LIKE(id), { isLike });
};
