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

import { City } from '../CitySearchBar/CitySearchBar';

interface SuggestionProps {
  queryWord: string;
  onItemSelect: (city: City) => void;
}

const Suggestion = ({ queryWord, onItemSelect }: SuggestionProps) => {
  const { suggestions, isFocused, setNewSuggestions, focusSuggestion } = useSuggestion({
    onItemSelect,
  });

  useEffect(() => {
    setNewSuggestions(queryWord);
  }, [queryWord]);

  const handleItemClick = (suggestion: City) => () => {
    onItemSelect(suggestion);
  };

  const handleItemMouseHover = (index: number) => () => {
    focusSuggestion(index);
  };

  return (
    <SuggestionList css={suggestionContainerStyling}>
      {suggestions.length ? (
        suggestions.map((city, index) => (
          <SuggestionsItem
            key={city.id}
            onClick={handleItemClick(city)}
            onMouseEnter={handleItemMouseHover(index)}
            css={getSuggestionItemStyling(isFocused(index))}
          >
            {city.name}
          </SuggestionsItem>
        ))
      ) : (
        <Text css={emptyTextStyling}>검색어에 해당하는 도시가 없습니다.</Text>
      )}
    </SuggestionList>
  );
};

export default Suggestion;
