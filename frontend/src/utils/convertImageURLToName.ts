export const convertImageURLToName = (url: string | null) => {
  if (url === null) return null;
  return url.split(process.env.IMAGE_BASEURL!)[1];
};
