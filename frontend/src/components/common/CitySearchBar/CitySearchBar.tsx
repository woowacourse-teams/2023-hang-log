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
  const { isOpen: isSuggestionOpen, open: openSuggestion, close: closeSuggestion } = useOverlay();
  const inputRef = useRef<HTMLInputElement>(null);

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
                  <CloseIcon css={closeIconStyling} />
                </Badge>
              ))}
            </span>
          )}
          <Input
            placeholder="방문 도시를 입력해주세요"
            value={queryWord}
            ref={inputRef}
            css={inputStyling}
          />
        </div>
        {isSuggestionOpen && (
          <MenuList css={suggestionContainer}>
            <MenuItem onClick={() => {}}>abc</MenuItem>
            <MenuItem onClick={() => {}}>123</MenuItem>
            <MenuItem onClick={() => {}}>456</MenuItem>
          </MenuList>
        )}
      </div>
    </Menu>
  );
};

export default CitySearchBar;
