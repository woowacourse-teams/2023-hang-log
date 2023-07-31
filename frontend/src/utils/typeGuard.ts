export function hasKeyInObject<T extends object>(
  obj: T,
  key: string | number | symbol
): key is keyof T {
  return Object.hasOwn(obj, key);
}
