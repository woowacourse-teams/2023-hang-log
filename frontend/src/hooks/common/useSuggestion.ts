import { useState } from 'react';

const cities = [
  '서울, 한국',
  '도쿄, 일본',
  '뉴욕, 미국',
  '런던, 영국',
  '파리, 프랑스',
  '로스앤젤레스, 미국',
  '시드니, 호주',
  '오클랜드, 뉴질랜드',
  '웰링턴, 뉴질랜드',
  '보스턴, 미국',
  '오하이오, 미국',
  '퀸즈타운, 뉴질랜드',
  '교토, 일본',
  '오사카, 일본',
  '부산, 한국',
  '제주도, 한국',
  '오클로호마, 미국',
];

const useSuggestion = () => {
  const [suggestions, setSuggestions] = useState<string[]>([]);
  const [focusedSuggestionIndex, setFocusedSuggestionIndex] = useState(-1);

  const setNewSuggestions = (word: string) => {
    const filteredSuggestions = cities.filter((suggestion) =>
      new RegExp(`^${word}`).test(suggestion)
    );

    setSuggestions(filteredSuggestions);
  };

  const focusUpperSuggestion = () => {
    setFocusedSuggestionIndex((prevIndex) =>
      prevIndex > 0 ? prevIndex - 1 : suggestions.length - 1
    );
  };

  const focusLowerSuggestion = () => {
    setFocusedSuggestionIndex((prevIndex) =>
      prevIndex < suggestions.length - 1 ? prevIndex + 1 : 0
    );
  };

  const focusSuggestion = (index: number) => {
    setFocusedSuggestionIndex(index);
  };

  return {
    suggestions,
    focusedSuggestionIndex,
    setNewSuggestions,
    focusLowerSuggestion,
    focusSuggestion,
    focusUpperSuggestion,
  };
};

export default useSuggestion;
