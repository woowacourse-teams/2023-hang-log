import { memo } from 'react';

import { Box, Input } from 'hang-log-design-system';

import { wrapperStyling } from '@components/trip/TripItemAddModal/PlaceInput/PlaceInput.style';

import { useTripItemPlaceInput } from '@hooks/trip/useTripItemPlaceInput';

import type { TripItemFormData } from '@type/tripItem';

interface PlaceInputProps {
  value: string;
  isError: boolean;
  isUpdatable: boolean;
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
  disableError: () => void;
}

const PlaceInput = ({
  value,
  isError,
  isUpdatable,
  updateInputValue,
  disableError,
}: PlaceInputProps) => {
  const { inputRef, handleEnterKeyClick, handlePlaceChange } = useTripItemPlaceInput(
    updateInputValue,
    disableError,
    isUpdatable
  );

  return (
    <Box css={wrapperStyling}>
      <Input
        ref={inputRef}
        value={value}
        isError={isError}
        label="장소"
        name="title"
        id="autocomplete"
        required
        placeholder="유효한 장소를 입력해 주세요"
        supportingText={isError ? '장소를 입력해 주세요' : undefined}
        onChange={handlePlaceChange}
        onKeyDown={handleEnterKeyClick}
      />
    </Box>
  );
};

export default memo(PlaceInput);
