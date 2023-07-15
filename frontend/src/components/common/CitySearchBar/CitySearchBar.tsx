import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
import { CITY } from '@constants/city';
import { Badge, Input, Menu, useOverlay } from 'hang-log-design-system';
import { FormEvent, useRef, useState } from 'react';

import {
  badgeStyling,
  closeIconStyling,
  container,
  inputStyling,
  tagListStyling,
  wrapper,
} from '@components/common/CitySearchBar/CitySearchBar.style';

import Suggestion from '../Suggestion/Suggestion';

interface CitySearchBarProps {
  initialCityTags: string[];
}

const CitySearchBar = ({ initialCityTags }: CitySearchBarProps) => {
  const [queryWord, setQueryWord] = useState('');
  const [cityTags, setCityTags] = useState<string[]>(initialCityTags);

  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);

  const searchCity = (e: FormEvent<HTMLInputElement>) => {
    const word = e.currentTarget.value;

    if (word === '') {
      closeSuggestion();
    }

    if (word !== '') {
      openSuggestion();
    }

    setQueryWord(word);
  };

  const addNewCity = (selectedCity: string) => {
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

  const CityTags = () =>
    cityTags.map((city) => (
      <Badge key={city} css={badgeStyling}>
        {city}
        <CloseIcon aria-label="remove tag" css={closeIconStyling} onClick={deleteCity(city)} />
      </Badge>
    ));

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
        {isSuggestionOpen && <Suggestion queryWord={queryWord} onItemSelect={addNewCity} />}
      </div>
    </Menu>
  );
};

export default CitySearchBar;
