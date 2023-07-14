import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
import { CITY } from '@constants/city';
import { Badge, Input, Menu, MenuItem, MenuList, Text, useOverlay } from 'hang-log-design-system';
import { FormEvent, useRef, useState } from 'react';

import {
  badgeStyling,
  closeIconStyling,
  container,
  emptyTextStyling,
  inputStyling,
  suggestionContainer,
  tagListStyling,
  wrapper,
} from '@components/common/CitySearchBar/CitySearchBar.style';

const cities = [
  'apple',
  'banana',
  'orange',
  'grape',
  'kiwi',
  'mango',
  'strawberry',
  'watermelon',
  'pineapple',
  'pear',
];

const CitySearchBar = () => {
  const [queryWord, setQueryWord] = useState('');
  const [cityTags, setCityTags] = useState<string[]>([]);
  const [suggestions, setSuggestions] = useState<string[]>(cities);
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
    setQueryWord('');
    setCityTags((cityTags) => {
      if (cityTags.includes(selectedCity)) {
        const newCityTags = cityTags.filter((cityTag) => cityTag !== selectedCity);
        return [...newCityTags, selectedCity];
      }

      if (cityTags.length >= CITY.MAX_NUM) {
        //toast로 경고 문구 띄우기
        return cityTags;
      }

      return [...cityTags, selectedCity];
    });

    closeSuggestion();
    inputRef.current?.focus();
  };

  const deleteCity = (selectedCity: string) => () => {
    setCityTags((cityTags) => cityTags.filter((cityTag) => cityTag !== selectedCity));

    inputRef.current?.focus();
  };

  const focusInput = () => {
    inputRef.current?.focus();
  };

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
        suggestions.map((suggestion) => (
          <MenuItem key={suggestion} onClick={addNewCity(suggestion)}>
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
              onInput={searchCity}
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
