import { Input } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import type { PasswordFormData } from '@type/adminMember';

import { ADMIN_MEMBER_MAX_LENGTH } from '@constants/ui';

interface CurrentPasswordInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof PasswordFormData>(key: K, value: PasswordFormData[K]) => void;
  disableError: () => void;
}

const CurrentPasswordInput = (
  { isError, value, updateInputValue, disableError }: CurrentPasswordInputProps
) => {
  const handleCurrentPasswordChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('currentPassword', event.target.value);
  };

  return (
    <Input
      type="password"
      label="기존 비밀번호"
      id="newPassword"
      name="newPassword"
      maxLength={ADMIN_MEMBER_MAX_LENGTH}
      value={value}
      placeholder="기존 비밀번호를 입력해 주세요"
      supportingText={isError ? '기존 비밀번호를 입력해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleCurrentPasswordChange}
    />
  );
};

export default memo(CurrentPasswordInput);
