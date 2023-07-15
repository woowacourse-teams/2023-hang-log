import { useCityTags } from '@/hooks/common/useCityTags';
import CloseIcon from '@assets/svg/close-icon.svg';
import SearchPinIcon from '@assets/svg/search-pin-icon.svg';
import { Badge, Input, Menu, useOverlay } from 'hang-log-design-system';
import { FormEvent, useRef, useState } from 'react';

import {
  badgeStyling,
  closeIconStyling,
  containerStyling,
  inputStyling,
  tagListStyling,
  wrapperStyling,
} from '@components/common/CitySearchBar/CitySearchBar.style';
import Suggestion from '@components/common/Suggestion/Suggestion';

interface CitySearchBarProps {
  initialCityTags: string[];
}

const CitySearchBar = ({ initialCityTags }: CitySearchBarProps) => {
  const [queryWord, setQueryWord] = useState('');
  const { cityTags, addCityTag, deleteCityTag } = useCityTags(initialCityTags);
  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);

  const handleInputChange = (e: FormEvent<HTMLInputElement>) => {
    const word = e.currentTarget.value;
    setQueryWord(word);

    openSuggestion();
  };

  const handleSuggestionClick = (selectedCity: string) => {
    addCityTag(selectedCity);
    resetAll();
  };

  const resetAll = () => {
    setQueryWord('');
    focusInput();
    closeSuggestion();
  };

  const handleDeleteButtonClick = (selectedCity: string) => () => {
    deleteCityTag(selectedCity);
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
        <CloseIcon
          aria-label="삭제 아이콘"
          css={closeIconStyling}
          onClick={handleDeleteButtonClick(city)}
        />
      </Badge>
    ));

  return (
    <Menu closeMenu={closeSuggestion}>
      <div css={containerStyling} onClick={focusInput}>
        <div css={wrapperStyling}>
          <SearchPinIcon aria-label="지도표시 아이콘" />
          <div css={tagListStyling}>
            <CityTags />
            <Input
              placeholder={cityTags.length ? '' : '방문 도시를 입력해주세요'}
              value={queryWord}
              onChange={handleInputChange}
              onFocus={handleInputFocus}
              ref={inputRef}
              css={inputStyling}
            />
          </div>
        </div>
        {isSuggestionOpen && (
          <Suggestion queryWord={queryWord} onItemSelect={handleSuggestionClick} />
        )}
      </div>
    </Menu>
  );
};

export default CitySearchBar;
