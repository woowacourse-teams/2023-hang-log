import { Button, Flex, Modal, Theme } from 'hang-log-design-system';

import { useAddAdminMemberForm } from '@/adminMember/hooks/useAddAdminMemberForm';

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
  const { adminMemberInformation, errors, disableError, updateInputValue, handleSubmit } =
    useAddAdminMemberForm({ onSuccess: onClose });

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
              isError={errors.isUsernameError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isUsernameError')}
            />
            <AdminTypeSelect
              value={adminMemberInformation.adminType}
              updateInputValue={updateInputValue}
            />
            <PasswordInput
              value={adminMemberInformation.password}
              isError={errors.isPasswordError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isPasswordError')}
            />
            <ConfirmPasswordInput
              value={adminMemberInformation.confirmPassword}
              isError={errors.isConfirmPasswordError}
              updateInputValue={updateInputValue}
              disableError={() => disableError('isConfirmPasswordError')}
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
