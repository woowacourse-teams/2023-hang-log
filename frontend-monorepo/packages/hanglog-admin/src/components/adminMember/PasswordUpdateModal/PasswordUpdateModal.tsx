import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

import { UseUpdatePasswordForm } from '@hooks/adminMember/useUpdatePasswordForm';

import CloseIcon from '@assets/svg/close-icon.svg?react';

import PasswordInput from './PasswordInput/PasswordInput';
import CurrentPasswordInput from './PasswordInput/CurrentPasswordInput';
import ConfirmPasswordInput from './PasswordInput/ConfirmPasswordInput';

import {
  wrapperStyling,
  formStyling,
  buttonStyling,
  closeButtonStyling,
  closeIconStyling,
} from './PasswordUpdateModal.style';

interface PasswordUpdateModalProps {
  adminMemberId: number;
  isOpen?: boolean;
  onClose: () => void;
}

const PasswordUpdateModal = ({
  adminMemberId,
  isOpen = true,
  onClose,
}: PasswordUpdateModalProps) => {
  const {
    adminMemberInformation,
    isCurrentPasswordError,
    isPasswordError,
    isConfirmPasswordError,
    disableCurrentPasswordError,
    disablePasswordError,
    disableConfirmPasswordError,
    updateInputValue,
    handleSubmit,
  } = UseUpdatePasswordForm({ adminMemberId: adminMemberId, onSuccess: onClose });

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
              value={adminMemberInformation.currentPassword}
              isError={isCurrentPasswordError}
              updateInputValue={updateInputValue}
              disableError={disableCurrentPasswordError}
            />
            <PasswordInput
              value={adminMemberInformation.newPassword}
              isError={isPasswordError}
              updateInputValue={updateInputValue}
              disableError={disablePasswordError}
            />
            <ConfirmPasswordInput
              value={adminMemberInformation.confirmPassword}
              isError={isConfirmPasswordError}
              updateInputValue={updateInputValue}
              disableError={disableConfirmPasswordError}
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
