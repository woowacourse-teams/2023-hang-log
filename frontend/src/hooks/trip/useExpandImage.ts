export const useExpandImage = (imageUrl: string) => {
  const getImageUrlWidthHeight = () => {
    const img = new Image();
    img.src = imageUrl;

    return { originalWidth: img.width, originalHeight: img.height };
  };

  const getExpandedImageWithHeight = () => {
    const { originalWidth, originalHeight } = getImageUrlWidthHeight();

    const orientation = originalHeight > originalWidth ? 'portrait' : 'landscape';

    if (orientation === 'portrait') {
      const height = 720;
      const width = Math.floor((originalWidth * height) / originalHeight);

      return { width, height };
    }

    const width = 600;
    const height = Math.floor((originalHeight * width) / originalWidth);

    return { width, height };
  };

  return { getExpandedImageWithHeight };
};
