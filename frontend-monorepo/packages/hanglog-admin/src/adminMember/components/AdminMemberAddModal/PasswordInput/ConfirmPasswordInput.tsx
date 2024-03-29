import { Input } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import type { AdminMemberFormData } from '@type/adminMember';

import { ADMIN_MEMBER_MAX_LENGTH } from '@constants/ui';

interface ConfirmPasswordInputProps {
  value: string;
  isError: boolean;
  updateInputValue: <K extends keyof AdminMemberFormData>(
    key: K,
    value: AdminMemberFormData[K]
  ) => void;
  disableError: () => void;
}

const ConfirmPasswordInput = (
  { isError, value, updateInputValue, disableError }: ConfirmPasswordInputProps
) => {
  const handleConfirmPasswordChange = (event: ChangeEvent<HTMLInputElement>) => {
    disableError();

    updateInputValue('confirmPassword', event.target.value);
  };

  return (
    <Input
      type="password"
      label="비밀번호 확인"
      id="confirmpassword"
      name="confirmpassword"
      maxLength={ADMIN_MEMBER_MAX_LENGTH}
      value={value}
      placeholder="비밀번호 한번 더 입력해 주세요"
      supportingText={isError ? '입력한 비밀번호를 다시한번 확인해 주세요' : undefined}
      isError={isError}
      required
      onChange={handleConfirmPasswordChange}
    />
  );
};

export default memo(ConfirmPasswordInput);
