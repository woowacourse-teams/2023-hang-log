export const REGEX = {
  ONLY_LETTER: /^[A-Za-z가-힣ㄱ-ㅎ]+$/g,
  ALPHABET_AND_KOREAN_CHARACTERS: /^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]+$/,
  HTTP_TO_HTTPS: /^http(?!s)/,
  ALPHABET: /^[A-Za-z]+$/,
  KOREAN_CHARACTERS: /^[가-힣]+$/,
  DATE: /^\d{4}-\d{2}-\d{2}$/,
};
