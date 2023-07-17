import { City } from '@/components/common/CitySearchBar/CitySearchBar';
import { useEffect, useState } from 'react';

const cities = [
  { id: 1, name: '서울, 한국' },
  { id: 2, name: '도쿄, 일본' },
  { id: 3, name: '뉴욕, 미국' },
  { id: 4, name: '런던, 영국' },
  { id: 5, name: '파리, 프랑스' },
  { id: 6, name: '로스앤젤레스, 미국' },
  { id: 7, name: '시드니, 호주' },
  { id: 15, name: '오클랜드, 뉴질랜드' },
  { id: 11, name: '웰링턴, 뉴질랜드' },
  { id: 12, name: '보스턴, 미국' },
  { id: 17, name: '오하이오, 미국' },
  { id: 19, name: '퀸즈타운, 뉴질랜드' },
  { id: 18, name: '교토, 일본' },
  { id: 16, name: '오사카, 일본' },
  { id: 13, name: '부산, 한국' },
  { id: 20, name: '제주도, 한국' },
  { id: 21, name: '오클로호마, 미국' },
];

export const useSuggestion = ({ onItemSelect }: { onItemSelect: (item: City) => void }) => {
  const [suggestions, setSuggestions] = useState<City[]>([]);
  const [focusedSuggestionIndex, setFocusedSuggestionIndex] = useState(-1);

  const setNewSuggestions = (word: string) => {
    const filteredSuggestions = cities.filter(({ name }) => new RegExp(`^${word}`).test(name));

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

  const isFocused = (index: number) => {
    return index === focusedSuggestionIndex;
  };

  const handleKeyPress = (e: globalThis.KeyboardEvent) => {
    if (e.key === 'ArrowUp') {
      focusUpperSuggestion();
    }

    if (e.key === 'ArrowDown') {
      focusLowerSuggestion();
    }

    if (e.key === 'Enter') {
      if (focusedSuggestionIndex >= 0) {
        onItemSelect(suggestions[focusedSuggestionIndex]);
      }
    }
  };

  useEffect(() => {
    window.addEventListener('keyup', handleKeyPress);

    return () => window.removeEventListener('keyup', handleKeyPress);
  });

  return {
    suggestions,
    focusedSuggestionIndex,
    setNewSuggestions,
    focusLowerSuggestion,
    focusSuggestion,
    focusUpperSuggestion,
    isFocused,
  };
};
