import { Button } from 'hang-log-design-system';

import {
  buttonStyling,
  deleteButtonStyling,
  formStyling,
  imageInputStyling,
} from '@components/myPage/EditUserProfileForm/EditUserProfileForm.style';
import NicknameInput from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput';
import ProfileImageInput from '@components/myPage/EditUserProfileForm/ProfileImageInput/ProfileImageInput';

import { useDeleteAccountMutation } from '@hooks/api/useDeleteAccountMutation';
import { useEditUserProfileForm } from '@hooks/member/useEditUserProfileForm';

import type { UserData } from '@type/member';

interface EditUserProfileForm {
  initialData: UserData;
}

const EditUserProfileForm = ({ initialData }: EditUserProfileForm) => {
  const { userInfo, isNicknameError, updateInputValue, disableNicknameError, handleSubmit } =
    useEditUserProfileForm(initialData);

  const deleteAccountMutation = useDeleteAccountMutation();

  const handleAccountDelete = () => {
    deleteAccountMutation.mutate();
  };

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
      <Button
        type="button"
        css={[buttonStyling, deleteButtonStyling]}
        variant="text"
        onClick={handleAccountDelete}
      >
        탈퇴하기
      </Button>
    </form>
  );
};

export default EditUserProfileForm;
