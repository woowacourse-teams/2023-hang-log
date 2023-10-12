const convertImageNames = (imageName: string[]) => {
  return imageName?.map((imageName) => `${process.env.IMAGE_BASEURL}${imageName}`);
};

export default convertImageNames;
