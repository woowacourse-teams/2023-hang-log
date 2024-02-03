const FIRST_CONSONANTS = [
  'ㄱ',
  'ㄲ',
  'ㄴ',
  'ㄷ',
  'ㄸ',
  'ㄹ',
  'ㅁ',
  'ㅂ',
  'ㅃ',
  'ㅅ',
  'ㅆ',
  'ㅇ',
  'ㅈ',
  'ㅉ',
  'ㅊ',
  'ㅋ',
  'ㅌ',
  'ㅍ',
  'ㅎ',
] as const;

const HANGUL_START_CHARCODE = '가'.charCodeAt(0);
const CHO_PERIOD = Math.floor('까'.charCodeAt(0) - '가'.charCodeAt(0));
const JUNG_PERIOD = Math.floor('개'.charCodeAt(0) - '가'.charCodeAt(0));

/**
 *
 * @param cho
 * @param jung
 * @param jong
 *
 * @returns
 */
const combine = (cho: number, jung: number, jong: number) => {
  return String.fromCharCode(HANGUL_START_CHARCODE + cho * CHO_PERIOD + jung * JUNG_PERIOD + jong);
};

/**
 * 초성으로 정규 표현식을 만드는 함수
 * @param query
 * @returns
 */
export const makeRegexByCho = (query: string) => {
  const regex = FIRST_CONSONANTS.reduce(
    (acc, cho, index) =>
      acc.replace(new RegExp(cho, 'g'), `[${combine(index, 0, 0)}-${combine(index + 1, 0, -1)}]`),
    query
  );

  return new RegExp(`^(${regex})`, 'g');
};
