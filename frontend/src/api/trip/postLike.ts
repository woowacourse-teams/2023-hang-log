import { axiosInstance } from '@api/axiosInstance';

import { END_POINTS } from '@constants/api';

interface postLikeParams {
  id: number;
  isLike: boolean;
}

export const postLike = ({ id, isLike }: postLikeParams) => {
  return axiosInstance.post(END_POINTS.LIKE(id), { isLike });
};
