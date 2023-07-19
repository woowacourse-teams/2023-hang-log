import type { CityData } from '@type/city';
import {
  MenuList as SuggestionList,
  MenuItem as SuggestionsItem,
  Text,
} from 'hang-log-design-system';
import { useEffect, useRef } from 'react';

import { useAutoScroll } from '@hooks/common/useAutoScroll';
import { useCitySuggestion } from '@hooks/common/useCitySuggestion';

import {
  containerStyling,
  emptyTextStyling,
  getItemStyling,
} from '@components/common/CitySuggestion/CitySuggestion.style';

interface SuggestionProps {
  queryWord: string;
  onItemSelect: (city: CityData) => void;
}

const CitySuggestion = ({ queryWord, onItemSelect }: SuggestionProps) => {
  const { suggestions, focusedSuggestionIndex, isFocused, setNewSuggestions } = useCitySuggestion({
    onItemSelect,
  });
  const listRef = useRef<HTMLDivElement>(null);
  const itemRef = useRef<HTMLLIElement>(null);
  const { scrollToFocusedItem } = useAutoScroll(listRef, itemRef);

  useEffect(() => {
    setNewSuggestions(queryWord);
  }, [queryWord]);

  const handleItemClick = (city: CityData) => () => {
    onItemSelect(city);
  };

  useEffect(() => {
    scrollToFocusedItem();
  }, [focusedSuggestionIndex]);

  return (
    <SuggestionList css={containerStyling} ref={listRef}>
      {suggestions.length ? (
        suggestions.map((city, index) => (
          <SuggestionsItem
            key={city.id}
            onClick={handleItemClick(city)}
            css={getItemStyling(isFocused(index))}
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
