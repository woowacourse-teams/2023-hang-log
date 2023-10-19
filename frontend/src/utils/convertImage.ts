export const convertToImageUrl = (imageName: string | null) => {
  return `${process.env.IMAGE_BASEURL}${imageName}`;
};

export const convertToImageUrls = (imageNames: string[]) => {
  return [...imageNames]?.map((imageName) => `${process.env.IMAGE_BASEURL}${imageName}`);
};

export const convertToImageNames = (imageUrls: string[]) => {
  return [...imageUrls]?.map((imageUrl) =>
    imageUrl.replace('blob:', '').replace(`${process.env.IMAGE_BASEURL}`, '')
  );
};
