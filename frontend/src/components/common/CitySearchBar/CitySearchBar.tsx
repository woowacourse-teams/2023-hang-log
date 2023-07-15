import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
import { CITY } from '@constants/city';
import { Badge, Input, Menu, MenuItem, MenuList, Text, useOverlay } from 'hang-log-design-system';
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

import useKeyPress from './useKeyPress';

const cities = [
  '서울, 한국',
  '도쿄, 일본',
  '뉴욕, 미국',
  '런던, 영국',
  '파리, 프랑스',
  '로스앤젤레스, 미국',
  '시드니, 호주',
  '오클랜드, 뉴질랜드',
  '웰링턴, 뉴질랜드',
  '보스턴, 미국',
  '오하이오, 미국',
  '퀸즈타운, 뉴질랜드',
  '교토, 일본',
  '오사카, 일본',
  '부산, 한국',
  '제주도, 한국',
  '오클로호마, 미국',
];

interface CitySearchBarProps {
  initialCityTags: string[];
}

const CitySearchBar = ({ initialCityTags }: CitySearchBarProps) => {
  const [queryWord, setQueryWord] = useState('');
  const [cityTags, setCityTags] = useState<string[]>(initialCityTags);
  const [suggestions, setSuggestions] = useState<string[]>(cities);
  const [selectedSuggestionIndex, setSelectedSuggestionIndex] = useState(-1);
  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);

  const searchCity = (e: FormEvent<HTMLInputElement>) => {
    const word = e.currentTarget.value;

    if (word === '') {
      closeSuggestion();
    }

    if (word !== '') {
      const filteredSuggestions = cities.filter((suggestion) => new RegExp(word).test(suggestion));

      setSuggestions(filteredSuggestions);
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
    setSelectedSuggestionIndex(-1);
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

    if (e.key === 'ArrowUp') {
      setSelectedSuggestionIndex((prevIndex) =>
        prevIndex > 0 ? prevIndex - 1 : suggestions.length - 1
      );
    }

    if (e.key === 'ArrowDown') {
      setSelectedSuggestionIndex((prevIndex) =>
        prevIndex < suggestions.length - 1 ? prevIndex + 1 : 0
      );
    }

    if (e.key === 'Enter') {
      if (selectedSuggestionIndex >= 0) {
        addNewCity(suggestions[selectedSuggestionIndex])();
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

  const Suggestions = () => (
    <MenuList css={suggestionContainer}>
      {suggestions.length ? (
        suggestions.map((suggestion, index) => (
          <MenuItem
            key={suggestion}
            onClick={addNewCity(suggestion)}
            onMouseEnter={() => setSelectedSuggestionIndex(index)}
            css={getMenuItemStyling(selectedSuggestionIndex === index)}
          >
            {suggestion}
          </MenuItem>
        ))
      ) : (
        <Text css={emptyTextStyling}>검색어에 해당하는 도시가 없습니다.</Text>
      )}
    </MenuList>
  );

  return (
    <Menu closeMenu={closeSuggestion}>
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
        {isSuggestionOpen && <Suggestions />}
      </div>
    </Menu>
  );
};

export default CitySearchBar;
