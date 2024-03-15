import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

import { useUpdatePasswordForm } from '@/adminMember/hooks/useUpdatePasswordForm';

import CloseIcon from '@assets/svg/close-icon.svg?react';

import ConfirmPasswordInput from './PasswordInput/ConfirmPasswordInput';
import CurrentPasswordInput from './PasswordInput/CurrentPasswordInput';
import PasswordInput from './PasswordInput/PasswordInput';
import {
  buttonStyling,
  closeButtonStyling,
  closeIconStyling,
  formStyling,
  wrapperStyling,
} from './PasswordUpdateModal.style';

interface PasswordUpdateModalProps {
  adminMemberId: number;
  isOpen?: boolean;
  onClose: () => void;
}

const PasswordUpdateModal = (
  { adminMemberId, isOpen = true, onClose }: PasswordUpdateModalProps
) => {
  const { passwordFormData, errors, disableError, updateInputValue, handleSubmit } =
    useUpdatePasswordForm({ adminMemberId: adminMemberId, onSuccess: onClose });

  return (
    <>
      <Modal
        css={wrapperStyling}
        isOpen={isOpen}
        closeModal={onClose}
        isBackdropClosable={false}
        hasCloseButton={false}
      >
        <button
          type="button"
          aria-label="모달 닫기 버튼"
          onClick={onClose}
          css={closeButtonStyling}
        >
          <CloseIcon css={closeIconStyling} />
        </button>
        <form css={formStyling} onSubmit={handleSubmit} noValidate>
          <Flex styles={{ direction: 'column', gap: Theme.spacer.spacing3, align: 'stretch' }}>
            <CurrentPasswordInput
              value={passwordFormData.currentPassword}
              isError={errors.isCurrentPasswordError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isCurrentPasswordError')}
            />
            <PasswordInput
              value={passwordFormData.newPassword}
              isError={errors.isNewPasswordError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isPasswordError')}
            />
            <ConfirmPasswordInput
              value={passwordFormData.confirmPassword}
              isError={errors.isConfirmPasswordError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isConfirmPasswordError')}
            />
          </Flex>
          <Button css={buttonStyling} variant="primary">
            비밀번호 변경하기
          </Button>
        </form>
      </Modal>
    </>
  );
};

export default PasswordUpdateModal;
