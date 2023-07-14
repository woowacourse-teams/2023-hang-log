import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
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

const fruits = [
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
  const [cities, setCities] = useState<string[]>([]);
  const [suggestions, setSuggestions] = useState<string[]>(fruits);
  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);

  const searchCity = (e: FormEvent<HTMLInputElement>) => {
    const word = e.currentTarget.value;

    if (word !== '') {
      const filteredSuggestions = fruits.filter((suggestion) => new RegExp(word).test(suggestion));

      setSuggestions(filteredSuggestions);
      openSuggestion();
    }

    setQueryWord(word);
  };

  const addNewCity = (selectedCity: string) => () => {
    setQueryWord('');
    setCities((cities) => {
      if (cities.includes(selectedCity)) {
        const newCities = cities.filter((city) => city !== selectedCity);
        return [...newCities, selectedCity];
      }

      return [...cities, selectedCity];
    });

    closeSuggestion();
    inputRef.current?.focus();
  };

  const deleteCity = (selectedCity: string) => () => {
    setCities((cities) => cities.filter((city) => city !== selectedCity));

    inputRef.current?.focus();
  };

  const CityTags = () =>
    cities.length ? (
      <span css={tagListStyling}>
        {cities.map((city) => (
          <Badge key={city} css={badgeStyling}>
            {city}
            <CloseIcon css={closeIconStyling} onClick={deleteCity(city)} />
          </Badge>
        ))}
      </span>
    ) : null;

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
      <div css={container}>
        <div css={wrapper}>
          <SearchPinIcon />
          <CityTags />
          <Input
            placeholder="방문 도시를 입력해주세요"
            value={queryWord}
            onInput={searchCity}
            ref={inputRef}
            css={inputStyling}
          />
        </div>
        {isSuggestionOpen && <Suggestions />}
      </div>
    </Menu>
  );
};

export default CitySearchBar;
