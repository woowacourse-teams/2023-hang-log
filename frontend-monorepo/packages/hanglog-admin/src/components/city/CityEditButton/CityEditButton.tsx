import { useOverlay } from 'hang-log-design-system';

import CityAddModal from '../CityAddModal/CityAddModal';

import type { CityData } from '@type/city';

import { buttonStyling, editIconStyling } from './CityEditButton.style';

import EditIcon from '@assets/svg/edit-icon.svg?react';

const CityEditButton = ({ ...information }: CityData) => {
  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();

  return (
    <>
      <button type="button" aria-label="도시 수정 버튼" onClick={openEditModal} css={buttonStyling}>
        <EditIcon css={editIconStyling} />
      </button>
      {isEditModalOpen && (
        <CityAddModal onClose={closeEditModal} cityId={information.id} initialData={information} />
      )}
    </>
  );
};

export default CityEditButton;
