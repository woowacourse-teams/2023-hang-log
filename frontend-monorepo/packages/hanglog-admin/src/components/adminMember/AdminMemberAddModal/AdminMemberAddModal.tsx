import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

import { UseAddAdminMemberForm } from '@hooks/adminMember/useAddAdminMemberForm';

import CloseIcon from '@assets/svg/close-icon.svg?react';

import {
  buttonStyling,
  closeButtonStyling,
  closeIconStyling,
  formStyling,
  wrapperStyling,
} from './AdminMemberAddModal.style';
import AdminTypeSelect from './AdminTypeSelect/AdminTypeSelect';
import ConfirmPasswordInput from './PasswordInput/ConfirmPasswordInput';
import PasswordInput from './PasswordInput/PasswordInput';
import UsernameInput from './UsernameInput/UsernameInput';

interface AdminMemberAddModalProps {
  isOpen?: boolean;
  onClose: () => void;
}

const AdminMemberAddModal = ({ isOpen = true, onClose }: AdminMemberAddModalProps) => {
  const {
    adminMemberInformation,
    isUsernameError,
    isPasswordError,
    isConfirmPasswordError,
    disableUsernameError,
    disablePasswordError,
    disableConfirmPasswordError,
    updateInputValue,
    handleSubmit,
  } = UseAddAdminMemberForm({ onSuccess: onClose });

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
            <UsernameInput
              value={adminMemberInformation.username}
              isError={isUsernameError}
              updateInputValue={updateInputValue}
              disableError={disableUsernameError}
            />
            <AdminTypeSelect
              value={adminMemberInformation.adminType}
              updateInputValue={updateInputValue}
            />
            <PasswordInput
              value={adminMemberInformation.password}
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
            관리자 추가하기
          </Button>
        </form>
      </Modal>
    </>
  );
};

export default AdminMemberAddModal;
