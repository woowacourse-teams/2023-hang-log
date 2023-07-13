import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
import { Badge, Input, Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';
import { FormEvent, useRef, useState } from 'react';

import {
  badgeStyling,
  closeIconStyling,
  container,
  inputStyling,
  suggestionContainer,
  tagListStyling,
  wrapper,
} from '@components/common/CitySearchBar/CitySearchBar.style';

const CitySearchBar = () => {
  const [queryWord, setQueryWord] = useState('');
  const [cities, setCities] = useState<string[]>([]);
  const [suggestions, setSuggestions] = useState<string[]>([]);
  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);

  const searchCity = (e: FormEvent<HTMLInputElement>) => {
    const word = e.currentTarget.value;

    if (word !== '') {
      //city받아와서 suggestions에 넣기
      openSuggestion();
    }

    setQueryWord(word);
  };

  const addNewCity = (chosenCity: string) => () => {
    setQueryWord('');
    setCities((cities) => [...cities, chosenCity]);

    closeSuggestion();
    inputRef.current?.focus();
  };

  const deleteCity = (selectedCity: string) => () => {
    setCities((cities) => cities.filter((city) => city !== selectedCity));

    inputRef.current?.focus();
  };

  return (
    <Menu closeMenu={closeSuggestion}>
      <div css={container}>
        <div css={wrapper}>
          <SearchPinIcon />
          {!!cities.length && (
            <span css={tagListStyling}>
              {cities.map((city) => (
                <Badge key={city} css={badgeStyling}>
                  {city}
                  <CloseIcon css={closeIconStyling} onClick={deleteCity(city)} />
                </Badge>
              ))}
            </span>
          )}
          <Input
            placeholder="방문 도시를 입력해주세요"
            value={queryWord}
            onChange={searchCity}
            ref={inputRef}
            css={inputStyling}
          />
        </div>
        {isSuggestionOpen && (
          <MenuList css={suggestionContainer}>
            {suggestions.map((suggestion) => (
              <MenuItem key={suggestion} onClick={addNewCity(suggestion)}>
                {suggestion}
              </MenuItem>
            ))}
          </MenuList>
        )}
      </div>
    </Menu>
  );
};

export default CitySearchBar;
