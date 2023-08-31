import { axiosInstance } from '@api/axiosInstance';

import type { ImageData } from '@type/image';

import { END_POINTS } from '@constants/api';

interface PostImageRequestBody {
  images: FormData;
}

export const postImage = async ({ images }: PostImageRequestBody) => {
  const { data } = await axiosInstance.post<ImageData>(END_POINTS.IMAGES, images);

  return data;
};
