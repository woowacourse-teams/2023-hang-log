export const limitToRange = (value: number, minValue: number, maxValue: number) => {
  if (value < minValue) return minValue;

  if (value > maxValue) return maxValue;

  return value;
};
