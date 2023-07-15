import { useSuggestion } from '@/hooks/common/useSuggestion';
import {
  MenuList as SuggestionList,
  MenuItem as SuggestionsItem,
  Text,
} from 'hang-log-design-system';
import { useEffect } from 'react';

import {
  emptyTextStyling,
  getSuggestionItemStyling,
  suggestionContainerStyling,
} from '@components/common/Suggestion/Suggestion.style';
import useSuggestion from '@components/common/Suggestion/useSuggestion';

interface SuggestionProps {
  queryWord: string;
  onItemSelect: (item: string) => void;
}

const Suggestion = ({ queryWord, onItemSelect }: SuggestionProps) => {
  const {
    suggestions,
    focusedSuggestionIndex,
    setNewSuggestions,
    focusLowerSuggestion,
    focusUpperSuggestion,
    focusSuggestion,
  } = useSuggestion();

  useEffect(() => {
    setNewSuggestions(queryWord);
  }, [queryWord]);

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

  return (
    <SuggestionList css={suggestionContainerStyling}>
      {suggestions.length ? (
        suggestions.map((suggestion, index) => (
          <SuggestionsItem
            key={suggestion}
            onClick={() => onItemSelect(suggestion)}
            onMouseEnter={() => focusSuggestion(index)}
            css={getSuggestionItemStyling(focusedSuggestionIndex === index)}
          >
            {suggestion}
          </SuggestionsItem>
        ))
      ) : (
        <Text css={emptyTextStyling}>검색어에 해당하는 도시가 없습니다.</Text>
      )}
    </SuggestionList>
  );
};

export default Suggestion;
