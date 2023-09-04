import { Flex, useOverlay } from 'hang-log-design-system';

import {
  binIconStyling,
  editIconStyling,
  getEditMenuStyling,
} from '@components/common/TripItem/EditMenu/EditMenu.style';
import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

import { useDeleteTripItemMutation } from '@hooks/api/useDeleteTripItemMutation';

import type { TripItemData } from '@type/tripItem';

import BinIcon from '@assets/svg/bin-icon.svg';
import EditIcon from '@assets/svg/edit-icon.svg';

interface EditMenuProps extends TripItemData {
  tripId: number;
  dayLogId: number;
  hasImage: boolean;
  imageHeight: number;
}

const EditMenu = ({ tripId, dayLogId, hasImage, imageHeight, ...information }: EditMenuProps) => {
  const deleteTripItemMutation = useDeleteTripItemMutation();

  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();

  const handleTripItemDelete = () => {
    deleteTripItemMutation.mutate({ tripId, itemId: information.id });
  };

  return (
    <>
      <Flex
        styles={{ direction: 'column', gap: '12px' }}
        css={getEditMenuStyling(hasImage, imageHeight)}
      >
        <EditIcon css={editIconStyling} role="button" aria-label="수정" onClick={openEditModal} />
        <BinIcon
          css={binIconStyling}
          role="button"
          aria-label="삭제"
          onClick={handleTripItemDelete}
        />
      </Flex>
      {isEditModalOpen && (
        <TripItemAddModal
          tripId={tripId}
          itemId={information.id}
          dayLogId={dayLogId}
          onClose={closeEditModal}
          initialData={{
            itemType: information.itemType,
            dayLogId,
            title: information.title,
            isPlaceUpdated: false,
            place: null,
            rating: information.rating,
            expense: information.expense
              ? {
                  currency: information.expense.currency,
                  amount: information.expense.amount,
                  categoryId: information.expense.category.id,
                }
              : null,
            memo: information.memo,
            imageUrls: information.imageUrls,
          }}
        />
      )}
    </>
  );
};

export default EditMenu;
