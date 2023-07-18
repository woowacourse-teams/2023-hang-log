import {
  MenuList as SuggestionList,
  MenuItem as SuggestionsItem,
  Text,
} from 'hang-log-design-system';
import { useEffect, useRef } from 'react';

import { useCitySuggestion } from '@hooks/common/useCitySuggestion';

import { City } from '@components/common/CitySearchBar/CitySearchBar';
import {
  emptyTextStyling,
  getSuggestionItemStyling,
  suggestionContainerStyling,
} from '@components/common/CitySuggestion/CitySuggestion.style';

interface SuggestionProps {
  queryWord: string;
  onItemSelect: (city: City) => void;
}

const CitySuggestion = ({ queryWord, onItemSelect }: SuggestionProps) => {
  const { suggestions, focusedSuggestionIndex, isFocused, setNewSuggestions, focusSuggestion } =
    useCitySuggestion({
      onItemSelect,
    });
  const listRef = useRef<HTMLDivElement>(null);
  const itemRef = useRef<HTMLLIElement>(null);

  useEffect(() => {
    setNewSuggestions(queryWord);
  }, [queryWord]);

  const handleItemClick = (suggestion: City) => () => {
    onItemSelect(suggestion);
  };

  const handleItemMouseHover = (index: number) => () => {
    focusSuggestion(index);
  };

  const scrollToFocusedSuggestion = () => {
    const list = listRef.current;
    const focusedItem = itemRef.current;

    if (list && focusedItem) {
      const listRect = list.getBoundingClientRect();
      const focusedItemRect = focusedItem.getBoundingClientRect();

      const scrollOffset =
        focusedItemRect.top - listRect.top - listRect.height / 2 + focusedItemRect.height / 2;

      list.scrollTo({
        top: list.scrollTop + scrollOffset,
        behavior: 'smooth',
      });
    }
  };

  useEffect(() => {
    scrollToFocusedSuggestion();
  }, [focusedSuggestionIndex]);

  return (
    <SuggestionList css={suggestionContainerStyling} ref={listRef}>
      {suggestions.length ? (
        suggestions.map((city, index) => (
          <SuggestionsItem
            key={city.id}
            onClick={handleItemClick(city)}
            onMouseEnter={handleItemMouseHover(index)}
            css={getSuggestionItemStyling(isFocused(index))}
            ref={isFocused(index) ? itemRef : null}
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

export default CitySuggestion;
