import { useOverlay } from 'hang-log-design-system';

import type { CurrencyData } from '@type/currency';

import EditIcon from '@assets/svg/edit-icon.svg?react';

import CurrencyAddModal from '../CurrencyAddModal/CurrencyAddModal';
import { buttonStyling, editIconStyling } from './CurrencyEditButton.style';

const CurrencyEditButton = ({ ...information }: CurrencyData) => {
  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();

  return (
    <>
      <button
        type="button"
        aria-label="환율 정보 수정 버튼"
        onClick={openEditModal}
        css={buttonStyling}
      >
        <EditIcon css={editIconStyling} />
      </button>
      {isEditModalOpen && (
        <CurrencyAddModal
          onClose={closeEditModal}
          currencyId={information.id}
          initialData={information}
        />
      )}
    </>
  );
};

export default CurrencyEditButton;
