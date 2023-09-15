import { END_POINTS } from '@/constants/api';

import { axiosInstance } from '@api/axiosInstance';

interface postLikeParams {
  id: number;
  isLike: boolean;
}

export const postLike = ({ id, isLike }: postLikeParams) => {
  return axiosInstance.post(END_POINTS.LIKE(id), { isLike });
};
