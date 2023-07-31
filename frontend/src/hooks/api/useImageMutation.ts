import { useMutation } from '@tanstack/react-query';

import { postImage } from '@api/image/postImage';

export const useImageMutation = () => {
  const imageMutation = useMutation(postImage());

  return imageMutation;
};
