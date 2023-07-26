import { useQueryClient } from '@tanstack/react-query';
import type { CityData } from '@type/city';
import { useEffect, useState } from 'react';

import { makeRegexByCho } from '@utils/cityFilter';
import { stringToOnlyLetter } from '@utils/formatter';

export const useCitySuggestion = ({ onItemSelect }: { onItemSelect: (item: CityData) => void }) => {
  const queryClient = useQueryClient();
  const cityData = queryClient.getQueryData<CityData[]>(['city']);
  const [suggestions, setSuggestions] = useState<CityData[]>([]);
  const [focusedSuggestionIndex, setFocusedSuggestionIndex] = useState(-1);

  useEffect(() => {
    setFocusedSuggestionIndex(-1);
    focusOnlySuggestion();
  }, [suggestions]);

  const setNewSuggestions = (word: string) => {
    const query = stringToOnlyLetter(word);
    const regex = makeRegexByCho(query);

    if (cityData) {
      const filteredSuggestions = cityData.filter(({ name }) => regex.test(name));
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

  const focusOnlySuggestion = () => {
    if (suggestions.length === 1) {
      setFocusedSuggestionIndex(0);
    }
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

    if (e.key === 'Escape') {
      //이거 onClose 프롭스로 받기 싫어서 꼼수로 일단 구현함....
      onItemSelect(suggestions[-1]);
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
    focusUpperSuggestion,
    isFocused,
  };
};
