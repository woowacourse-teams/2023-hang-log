import { Link } from 'react-router-dom';

import { Button } from 'hang-log-design-system';

import {
  buttonStyling,
  formStyling,
  imageInputStyling,
  linkStyling,
} from '@components/myPage/EditUserProfileForm/EditUserProfileForm.style';
import NicknameInput from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput';
import ProfileImageInput from '@components/myPage/EditUserProfileForm/ProfileImageInput/ProfileImageInput';

import { useEditUserProfileForm } from '@hooks/member/useEditUserProfileForm';

import type { UserData } from '@type/member';

interface EditUserProfileForm {
  initialData: UserData;
}

const EditUserProfileForm = ({ initialData }: EditUserProfileForm) => {
  const { userInfo, isNicknameError, updateInputValue, disableNicknameError, handleSubmit } =
    useEditUserProfileForm(initialData);

  return (
    <form css={formStyling} onSubmit={handleSubmit} noValidate>
      <ProfileImageInput
        css={imageInputStyling}
        initialImageUrl={initialData.imageUrl}
        updateInputValue={updateInputValue}
      />
      <NicknameInput
        value={userInfo.nickname}
        isError={isNicknameError}
        updateInputValue={updateInputValue}
        disableError={disableNicknameError}
      />
      <Button variant="primary" css={buttonStyling}>
        수정하기
      </Button>
      {/* TODO : 탈퇴 구글 폼으로 변경해야 함 */}
      <Link to="hanglog.com" css={linkStyling}>
        탈퇴하기
      </Link>
    </form>
  );
};

export default EditUserProfileForm;
