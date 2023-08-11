import { Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';

import {
  getMoreMenuStyling,
  moreButtonStyling,
  moreMenuListStyling,
} from '@components/common/TripItem/EditMenu/EditMenu.style';
import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

import { useDeleteTripItemMutation } from '@hooks/api/useDeleteTripItemMutation';

import type { TripItemData } from '@type/tripItem';

import MoreIcon from '@assets/svg/more-icon.svg';

interface EditMenuProps extends TripItemData {
  tripId: number;
  dayLogId: number;
  hasImage: boolean;
  imageHeight: number;
}

const EditMenu = ({ tripId, dayLogId, hasImage, imageHeight, ...information }: EditMenuProps) => {
  const deleteTripItemMutation = useDeleteTripItemMutation();

  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();
  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();

  const handleTripItemDelete = () => {
    deleteTripItemMutation.mutate({ tripId, itemId: information.id });
  };

  return (
    <>
      <Menu css={getMoreMenuStyling(hasImage, imageHeight)} closeMenu={closeMenu}>
        <button css={moreButtonStyling} type="button" aria-label="더 보기 메뉴" onClick={openMenu}>
          <MoreIcon />
        </button>
        {isMenuOpen && (
          <MenuList css={moreMenuListStyling}>
            <MenuItem onClick={openEditModal}>수정</MenuItem>
            <MenuItem onClick={handleTripItemDelete}>삭제</MenuItem>
          </MenuList>
        )}
      </Menu>
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
