import { NICKNAME_MAX_LENGTH } from '@constants/ui';
import type { UserData } from '@type/member';
import { Input } from 'hang-log-design-system';
import { type ChangeEvent, useCallback } from 'react';

import { inputStyling } from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput.style';

interface NicknameInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof UserData>(key: K, value: UserData[K]) => void;
  disableError: () => void;
}

const NicknameInput = ({ value, isError, updateInputValue, disableError }: NicknameInputProps) => {
  const handleNicknameChange = useCallback(
    (event: ChangeEvent<HTMLInputElement>) => {
      disableError();

      updateInputValue('nickname', event.target.value);
    },
    [disableError, updateInputValue]
  );

  return (
    <Input
      css={inputStyling}
      id="nick-name"
      label="닉네임"
      required
      value={value}
      supportingText={isError ? '닉네임을 입력해 주세요' : undefined}
      isError={isError}
      placeholder="닉네임을 입력해 주세요"
      maxLength={NICKNAME_MAX_LENGTH}
      onChange={handleNicknameChange}
    />
  );
};

export default NicknameInput;
