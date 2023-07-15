import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
import { CITY } from '@constants/city';
import {
  Badge,
  Input,
  Menu as Suggestion,
  MenuList as SuggestionList,
  MenuItem as SuggestionsItem,
  Text,
  useOverlay,
} from 'hang-log-design-system';
import { FormEvent, useEffect, useRef, useState } from 'react';

import {
  badgeStyling,
  closeIconStyling,
  container,
  emptyTextStyling,
  getMenuItemStyling,
  inputStyling,
  suggestionContainer,
  tagListStyling,
  wrapper,
} from '@components/common/CitySearchBar/CitySearchBar.style';

import useCitySearchBar from './useCitySearchBar';

interface CitySearchBarProps {
  initialCityTags: string[];
}

const CitySearchBar = ({ initialCityTags }: CitySearchBarProps) => {
  const [queryWord, setQueryWord] = useState('');
  const [cityTags, setCityTags] = useState<string[]>(initialCityTags);
  const {
    suggestions,
    focusedSuggestionIndex,
    setNewSuggestions,
    focusLowerSuggestion,
    focusUpperSuggestion,
    focusSuggestion,
  } = useCitySearchBar();
  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);

  const searchCity = (e: FormEvent<HTMLInputElement>) => {
    const word = e.currentTarget.value;

    if (word === '') {
      closeSuggestion();
    }

    if (word !== '') {
      setNewSuggestions(word);
      openSuggestion();
    }

    setQueryWord(word);
  };

  const addNewCity = (selectedCity: string) => () => {
    const city = selectedCity.split(',')[0];

    setCityTags((cityTags) => {
      if (cityTags.includes(city)) {
        const newCityTags = cityTags.filter((cityTag) => cityTag !== city);
        return [...newCityTags, city];
      }

      if (cityTags.length >= CITY.MAX_NUM) {
        return cityTags;
      }

      return [...cityTags, city];
    });

    resetAll();
  };

  const resetAll = () => {
    setQueryWord('');
    focusSuggestion(-1);
    focusInput();
    closeSuggestion();
  };

  const deleteCity = (selectedCity: string) => () => {
    const city = selectedCity.split(',')[0];

    setCityTags((cityTags) => cityTags.filter((cityTag) => cityTag !== city));

    focusInput();
  };

  const focusInput = () => {
    inputRef.current?.focus();
  };

  const handleInputFocus = () => {
    if (queryWord) {
      openSuggestion();
    }
  };

  const handleKeyUpAndDown = (e: globalThis.KeyboardEvent) => {
    e.preventDefault();

    if (!isSuggestionOpen) return;

    if (e.key === 'ArrowUp') {
      focusUpperSuggestion();
    }

    if (e.key === 'ArrowDown') {
      focusLowerSuggestion();
    }

    if (e.key === 'Enter') {
      if (focusedSuggestionIndex >= 0) {
        addNewCity(suggestions[focusedSuggestionIndex])();
      }
    }
  };

  useEffect(() => {
    window.addEventListener('keyup', handleKeyUpAndDown);

    return () => window.removeEventListener('keyup', handleKeyUpAndDown);
  });

  const CityTags = () =>
    cityTags.map((city) => (
      <Badge key={city} css={badgeStyling}>
        {city}
        <CloseIcon aria-label="remove tag" css={closeIconStyling} onClick={deleteCity(city)} />
      </Badge>
    ));

  const SuggestionBox = () => (
    <SuggestionList css={suggestionContainer}>
      {suggestions.length ? (
        suggestions.map((suggestion, index) => (
          <SuggestionsItem
            key={suggestion}
            onClick={addNewCity(suggestion)}
            onMouseEnter={() => focusSuggestion(index)}
            css={getMenuItemStyling(focusedSuggestionIndex === index)}
          >
            {suggestion}
          </SuggestionsItem>
        ))
      ) : (
        <Text css={emptyTextStyling}>검색어에 해당하는 도시가 없습니다.</Text>
      )}
    </SuggestionList>
  );

  return (
    <Suggestion closeMenu={closeSuggestion}>
      <div css={container} onClick={focusInput}>
        <div css={wrapper}>
          <SearchPinIcon aria-label="map-pin icon" />
          <div css={tagListStyling}>
            <CityTags />
            <Input
              placeholder={cityTags.length ? '' : '방문 도시를 입력해주세요'}
              value={queryWord}
              onChange={searchCity}
              onFocus={handleInputFocus}
              ref={inputRef}
              css={inputStyling}
            />
          </div>
        </div>
        {isSuggestionOpen && <SuggestionBox />}
      </div>
    </Suggestion>
  );
};

export default CitySearchBar;
