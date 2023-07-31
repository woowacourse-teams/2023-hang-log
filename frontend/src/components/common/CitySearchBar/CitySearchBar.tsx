import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
import type { CityData } from '@type/city';
import { Badge, Input, Label, Menu, useOverlay } from 'hang-log-design-system';
import type { FormEvent, KeyboardEvent } from 'react';
import { useEffect, useRef, useState } from 'react';

import { useCityQuery } from '@hooks/api/useCityQuery';
import { useCityTags } from '@hooks/common/useCityTags';
import { useDebounce } from '@hooks/common/useDebounce';

import {
  badgeStyling,
  closeIconStyling,
  containerStyling,
  inputStyling,
  searchPinIconStyling,
  tagListStyling,
  wrapperStyling,
} from '@components/common/CitySearchBar/CitySearchBar.style';
import CitySuggestion from '@components/common/CitySuggestion/CitySuggestion';

interface CitySearchBarProps {
  initialCities?: CityData[];
  updateCityInfo: (cities: CityData[]) => void;
  required?: boolean;
}

const CitySearchBar = ({ initialCities, updateCityInfo, required = false }: CitySearchBarProps) => {
  const [queryWord, setQueryWord] = useState('');
  const { cityTags, addCityTag, deleteCityTag } = useCityTags(initialCities ?? []);
  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);
  const debouncedQueryWord = useDebounce(queryWord, 150);
  useCityQuery();

  useEffect(() => {
    updateCityInfo(cityTags);
  }, [cityTags, updateCityInfo]);

  const handleInputChange = (event: FormEvent<HTMLInputElement>) => {
    const word = event.currentTarget.value;
    setQueryWord(word);

    openSuggestion();
  };

  const handleSuggestionClick = (selectedCity: CityData) => {
    addCityTag(selectedCity);
    resetAll();
  };

  const resetAll = () => {
    setQueryWord('');
    focusInput();
    closeSuggestion();
  };

  const handleDeleteButtonClick = (selectedCity: CityData) => () => {
    deleteCityTag(selectedCity);
    focusInput();
  };

  const focusInput = () => {
    inputRef.current?.focus();
  };

  const handleInputFocus = () => {
    if (debouncedQueryWord) {
      openSuggestion();
    }
  };

  const preventSubmit = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      e.preventDefault();
    }
  };

  return (
    <Menu closeMenu={closeSuggestion}>
      <div css={containerStyling} onClick={focusInput}>
        <Label required={required}>방문 도시</Label>
        <div css={wrapperStyling}>
          <SearchPinIcon aria-label="지도표시 아이콘" css={searchPinIconStyling} />
          <div css={tagListStyling}>
            {cityTags.map((cityTag) => (
              <Badge key={cityTag.id} css={badgeStyling}>
                {cityTag.name}
                <CloseIcon
                  aria-label="삭제 아이콘"
                  css={closeIconStyling}
                  onClick={handleDeleteButtonClick(cityTag)}
                />
              </Badge>
            ))}
            <Input
              placeholder={cityTags.length ? '' : '방문 도시를 입력해주세요'}
              value={queryWord}
              onChange={handleInputChange}
              onFocus={handleInputFocus}
              onKeyDown={preventSubmit}
              ref={inputRef}
              css={inputStyling}
            />
          </div>
        </div>
        {isSuggestionOpen && (
          <CitySuggestion
            queryWord={debouncedQueryWord}
            onItemSelect={handleSuggestionClick}
            closeSuggestion={closeSuggestion}
          />
        )}
      </div>
    </Menu>
  );
};

export default CitySearchBar;
