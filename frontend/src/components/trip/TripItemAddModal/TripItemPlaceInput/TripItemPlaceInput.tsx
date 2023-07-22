import type { TripItemFormData } from '@type/tripItem';
import { Box, Input } from 'hang-log-design-system';
import { memo } from 'react';

import { useTripItemPlaceInput } from '@hooks/trip/useTripItemPlaceInput';

import { wrapperStyling } from '@components/trip/TripItemAddModal/TripItemPlaceInput/TripItemPlaceInput.style';

interface TripItemPlaceInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
  disableError: () => void;
}

const TripItemPlaceInput = ({
  value,
  isError,
  updateInputValue,
  disableError,
}: TripItemPlaceInputProps) => {
  const { inputRef, handleEnterKeyClick, handlePlaceChange } = useTripItemPlaceInput(
    isError,
    updateInputValue,
    disableError
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

export default memo(TripItemPlaceInput);
