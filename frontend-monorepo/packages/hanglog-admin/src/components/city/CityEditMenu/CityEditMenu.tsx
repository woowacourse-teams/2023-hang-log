import { useOverlay } from 'hang-log-design-system';

import CityAddModal from '../CityAddModal/CityAddModal';

import type { CityData } from '@/types/city';

import { buttonStyling, editIconStyling } from './CityEditMenu.style';

import EditIcon from '@assets/svg/edit-icon.svg?react';

const CityEditMenu = ({ ...information }: CityData) => {
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

export default CityEditMenu;
