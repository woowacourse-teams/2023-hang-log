import { END_POINTS } from '@/constants/api';

import { axiosInstance } from '@api/axiosInstance';

interface PostImageRequestBody {
  images: FormData;
}

export const postImage =
  () =>
  async ({ images }: PostImageRequestBody) => {
    const response = await axiosInstance.post<string[]>(END_POINTS.IMAGES, images);

    return response.data;
  };
