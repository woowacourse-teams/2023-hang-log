import { useSingleImageUpload } from '@/hooks/common/useSingleImageUpload';

import { useCallback, useRef } from 'react';

import { Box, Button, Flex, Heading, Modal, Text, useOverlay } from 'hang-log-design-system';

import {
  buttonStyling,
  deleteButtonStyling,
  formStyling,
  imageStyling,
  inputStyling,
  modalButtonContainerStyling,
  modalContentStyling,
  uploadButtonStyling,
  wrapperStyling,
} from '@components/myPage/EditUserProfileForm/EditUserProfileForm.style';
import NicknameInput from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput';

import { useDeleteAccountMutation } from '@hooks/api/useDeleteAccountMutation';
import { useEditUserProfileForm } from '@hooks/member/useEditUserProfileForm';

import type { UserData } from '@type/member';

interface EditUserProfileForm {
  initialData: UserData;
}

const EditUserProfileForm = ({ initialData }: EditUserProfileForm) => {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const { userInfo, isNicknameError, updateInputValue, disableNicknameError, handleSubmit } =
    useEditUserProfileForm(initialData);

  const {
    isOpen: isDeleteModalOpen,
    close: closeDeleteModal,
    open: openDeleteModal,
  } = useOverlay();

  const deleteAccountMutation = useDeleteAccountMutation();

  const handleAccountDelete = () => {
    deleteAccountMutation.mutate();
  };

  const handleImageUploadButtonClick = () => {
    inputRef.current?.click();
  };

  const handleImageUrlsChange = useCallback(
    (imageUrl: string) => {
      updateInputValue('imageUrl', imageUrl);
    },
    [updateInputValue]
  );

  const { isImageUploading, uploadedImageUrl, handleImageUpload } = useSingleImageUpload({
    initialImageUrl: initialData.imageUrl,
    onSuccess: handleImageUrlsChange,
  });

  return (
    <>
      <form css={formStyling} onSubmit={handleSubmit} noValidate>
        <Box css={wrapperStyling}>
          <Button
            css={uploadButtonStyling}
            type="button"
            size="small"
            variant="text"
            onClick={handleImageUploadButtonClick}
          >
            수정
          </Button>
          <input
            css={inputStyling}
            type="file"
            accept="image/*"
            id="profile-image"
            ref={inputRef}
            onChange={handleImageUpload}
          />
          <img css={imageStyling} src={uploadedImageUrl!} alt="사용자 프로필 이미지" />
        </Box>
        <NicknameInput
          value={userInfo.nickname}
          isError={isNicknameError}
          updateInputValue={updateInputValue}
          disableError={disableNicknameError}
        />
        <Button
          variant={isImageUploading ? 'default' : 'primary'}
          css={buttonStyling}
          disabled={isImageUploading}
        >
          수정하기
        </Button>
        <Button
          type="button"
          css={[buttonStyling, deleteButtonStyling]}
          variant="text"
          size="small"
          onClick={openDeleteModal}
        >
          탈퇴하기
        </Button>
      </form>
      <Modal isOpen={isDeleteModalOpen} closeModal={closeDeleteModal}>
        <Box css={modalContentStyling}>
          <Heading size="xSmall">계정 탈퇴를 하겠어요?</Heading>
          <Text>
            행록을 떠나보내야 한다니 아쉬워요. 행록을 탈퇴하고 싶다면 탈퇴 버튼을 눌러주세요.
          </Text>
          <Flex css={modalButtonContainerStyling}>
            <Button variant="default" onClick={closeDeleteModal}>
              취소
            </Button>
            <Button variant="danger" onClick={handleAccountDelete}>
              탈퇴
            </Button>
          </Flex>
        </Box>
      </Modal>
    </>
  );
};

export default EditUserProfileForm;
