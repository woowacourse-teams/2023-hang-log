import type { CityData } from '@type/city';
import { useEffect, useState } from 'react';

import { makeRegexByCho } from '@utils/cityFilter';

import { useCityQuery } from '@hooks/api/useCityQuery';

export const useCitySuggestion = ({ onItemSelect }: { onItemSelect: (item: CityData) => void }) => {
  const { citiesData } = useCityQuery();
  console.log(citiesData);
  const [suggestions, setSuggestions] = useState<CityData[]>([]);
  const [focusedSuggestionIndex, setFocusedSuggestionIndex] = useState(-1);

  const setNewSuggestions = (word: string) => {
    const regex = makeRegexByCho(word);

    if (citiesData) {
      const filteredSuggestions = citiesData.filter(({ name }) => regex.test(name));
      setSuggestions(filteredSuggestions);
    }
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
  }, [focusedSuggestionIndex]);

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
