import { NICKNAME_MAX_LENGTH } from '@constants/ui';
import { Input } from 'hang-log-design-system';

import { inputStyling } from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput.style';

const NicknameInput = () => {
  return (
    <Input
      css={inputStyling}
      id="nick-name"
      label="닉네임"
      required
      placeholder="닉네임을 입력해 주세요"
      maxLength={NICKNAME_MAX_LENGTH}
    />
  );
};

export default NicknameInput;
