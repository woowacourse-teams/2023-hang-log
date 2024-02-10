import { useOverlay } from 'hang-log-design-system';

import CategoryAddModal from '../CategoryAddModal/CategoryAddModal';

import type { CategoryData } from '@type/category';

import { buttonStyling, editIconStyling } from './CategoryEditButton.style';

import EditIcon from '@assets/svg/edit-icon.svg?react';

const CategoryEditButton = ({ ...information }: CategoryData) => {
  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();

  return (
    <>
      <button
        type="button"
        aria-label="카테고리 수정 버튼"
        onClick={openEditModal}
        css={buttonStyling}
      >
        <EditIcon css={editIconStyling} />
      </button>
      {isEditModalOpen && (
        <CategoryAddModal
          onClose={closeEditModal}
          originalCategoryId={information.id}
          initialData={information}
        />
      )}
    </>
  );
};

export default CategoryEditButton;
