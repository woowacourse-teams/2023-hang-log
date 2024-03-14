import { useOverlay } from 'hang-log-design-system';

import EditIcon from '@assets/svg/edit-icon.svg?react';

import PasswordUpdateModal from '../PasswordUpdateModal/PasswordUpdateModal';
import { buttonStyling, editIconStyling } from './PasswordEditButton.style';

interface PasswordEditButtonProps {
  id: number;
}

const PasswordEditButton = ({ id }: PasswordEditButtonProps) => {
  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();

  return (
    <>
      <button
        type="button"
        aria-label="비밀번호 수정 버튼"
        onClick={openEditModal}
        css={buttonStyling}
      >
        <EditIcon css={editIconStyling} />
      </button>
      {isEditModalOpen && <PasswordUpdateModal onClose={closeEditModal} adminMemberId={id} />}
    </>
  );
};

export default PasswordEditButton;
