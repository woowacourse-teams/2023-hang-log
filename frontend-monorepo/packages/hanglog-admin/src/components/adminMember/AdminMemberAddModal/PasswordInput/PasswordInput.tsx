import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { Input } from 'hang-log-design-system';

import type { AdminMemberFormData } from '@/types/adminMember';

import { ADMIN_MEMBER_MAX_LENGTH } from '@/constants/ui';

interface PasswordInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof AdminMemberFormData>(
    key: K,
    value: AdminMemberFormData[K]
  ) => void;
  disableError: () => void;
}

const PasswordInput = ({ isError, value, updateInputValue, disableError }: PasswordInputProps) => {
  const handlePasswordChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('password', event.target.value);
  };

  return (
    <Input
      type="password"
      label="비밀번호"
      id="password"
      name="password"
      maxLength={ADMIN_MEMBER_MAX_LENGTH}
      value={value}
      placeholder="4자 이상의 비밀번호를 입력해 주세요"
      supportingText={isError ? '비밀번호를 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handlePasswordChange}
    />
  );
};

export default memo(PasswordInput);
