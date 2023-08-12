import { useCallback, useEffect, useState } from 'react';

import { useQueryClient } from '@tanstack/react-query';

import { makeRegexByCho } from '@utils/cityFilter';
import { formatStringToLetter } from '@utils/formatter';

import type { CityData } from '@type/city';

interface useCitySuggestionPrams {
  onItemSelect: (city: CityData) => void;
}

export const useCitySuggestion = ({ onItemSelect }: useCitySuggestionPrams) => {
  const queryClient = useQueryClient();
  const cityData = queryClient.getQueryData<CityData[]>(['city']);
  const [suggestions, setSuggestions] = useState<CityData[]>([]);
  const [focusedSuggestionIndex, setFocusedSuggestionIndex] = useState(-1);

  const setNewSuggestions = (word: string) => {
    const query = formatStringToLetter(word);
    const regex = makeRegexByCho(query);

    if (cityData) {
      const filteredSuggestions = cityData.filter(({ name }) => regex.test(name));
      setSuggestions(filteredSuggestions);
    }
  };

  const focusUpperSuggestion = useCallback(() => {
    setFocusedSuggestionIndex((prevIndex) =>
      prevIndex > 0 ? prevIndex - 1 : suggestions.length - 1
    );
  }, [suggestions.length]);

  const focusLowerSuggestion = useCallback(() => {
    setFocusedSuggestionIndex((prevIndex) =>
      prevIndex < suggestions.length - 1 ? prevIndex + 1 : 0
    );
  }, [suggestions.length]);

  const focusOnlySuggestion = useCallback(() => {
    if (suggestions.length === 1) {
      setFocusedSuggestionIndex(0);
    }
  }, [suggestions.length]);

  const isFocused = (index: number) => {
    return index === focusedSuggestionIndex;
  };

  const handleKeyPress = useCallback(
    (e: globalThis.KeyboardEvent) => {
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
    },
    [focusLowerSuggestion, focusUpperSuggestion, focusedSuggestionIndex, onItemSelect, suggestions]
  );

  useEffect(() => {
    setFocusedSuggestionIndex(-1);
    focusOnlySuggestion();
  }, [focusOnlySuggestion, suggestions]);

  useEffect(() => {
    window.addEventListener('keyup', handleKeyPress);

    return () => window.removeEventListener('keyup', handleKeyPress);
  }, [focusedSuggestionIndex, handleKeyPress]);

  return {
    suggestions,
    focusedSuggestionIndex,
    setNewSuggestions,
    focusLowerSuggestion,
    focusUpperSuggestion,
    isFocused,
  };
};
