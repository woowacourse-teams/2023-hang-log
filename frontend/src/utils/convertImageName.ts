const convertImageName = (imageName: string | null) => {
  return `${process.env.IMAGE_BASEURL}${imageName}`;
};

export default convertImageName;
