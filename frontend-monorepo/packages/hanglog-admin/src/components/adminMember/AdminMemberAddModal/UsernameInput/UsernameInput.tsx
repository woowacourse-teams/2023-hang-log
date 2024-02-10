import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { AdminMemberFormData } from '@type/adminMember';

import { ADMIN_MEMBER_MAX_LENGTH } from '@constants/ui';

interface UsernameInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof AdminMemberFormData>(
    key: K,
    value: AdminMemberFormData[K]
  ) => void;
  disableError: () => void;
}

const UsernameInput = ({ isError, value, updateInputValue, disableError }: UsernameInputProps) => {
  const handleUsernameChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('username', event.target.value);
  };

  return (
    <Input
      label="계정명"
      id="username"
      name="username"
      maxLength={ADMIN_MEMBER_MAX_LENGTH}
      value={value}
      placeholder="계정명을 입력해 주세요"
      supportingText={isError ? '계정명을 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleUsernameChange}
    />
  );
};

export default memo(UsernameInput);
