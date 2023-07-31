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
  closeSuggestion: () => void;
}

const CitySuggestion = ({ queryWord, onItemSelect, closeSuggestion }: SuggestionProps) => {
  const { suggestions, focusedSuggestionIndex, isFocused, setNewSuggestions } = useCitySuggestion({
    onItemSelect,
    closeSuggestion,
  });
  const listRef = useRef<HTMLUListElement>(null);
  const itemRef = useRef<HTMLLIElement>(null);
  const { scrollToFocusedItem } = useAutoScroll(listRef, itemRef);

  useEffect(() => {
    if (!queryWord) return;

    setNewSuggestions(queryWord);
  }, [queryWord]);

  useEffect(() => {
    scrollToFocusedItem();
  }, [focusedSuggestionIndex]);

  const handleItemClick = (city: CityData) => () => {
    onItemSelect(city);
  };

  return (
    // eslint-disable-next-line react/jsx-no-useless-fragment
    <>
      {queryWord && (
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
      )}
    </>
  );
};

export default CitySuggestion;
