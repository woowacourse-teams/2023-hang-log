export const getDayLengthFromDateRange = (startDate: string | null, endDate: string | null) => {
  if (!startDate || !endDate) return Infinity;

  const start = new Date(startDate);
  const end = new Date(endDate);

  const timeDiff = Math.abs(end.getTime() - start.getTime());

  return Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
};
