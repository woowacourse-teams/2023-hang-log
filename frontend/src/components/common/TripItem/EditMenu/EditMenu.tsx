import MoreIcon from '@assets/svg/more-icon.svg';
import type { TripItemData } from '@type/tripItem';
import { Menu, MenuItem, MenuList, Toast, useOverlay } from 'hang-log-design-system';

import { useDeleteTripItemMutation } from '@hooks/api/useDeleteTripItemMutation';

import {
  moreButtonStyling,
  moreMenuListStyling,
  moreMenuStyling,
} from '@components/common/TripItem/EditMenu/EditMenu.style';
import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

interface EditMenuProps extends TripItemData {
  tripId: number;
  dayLogId: number;
}

const EditMenu = ({ tripId, dayLogId, ...information }: EditMenuProps) => {
  const deleteTripItemMutation = useDeleteTripItemMutation();

  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();
  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();
  const { isOpen: isErrorTostOpen, open: openErrorToast, close: closeErrorToast } = useOverlay();

  const handleTripItemDelete = () => {
    deleteTripItemMutation.mutate(
      { tripId, itemId: information.id },
      {
        onError: () => {
          openErrorToast();
          closeMenu();
        },
      }
    );
  };

  return (
    <>
      <Menu css={moreMenuStyling} closeMenu={closeMenu}>
        <button css={moreButtonStyling} type="button" onClick={openMenu}>
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
      {isErrorTostOpen && (
        <Toast variant="error" closeToast={closeErrorToast}>
          아이템 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.
        </Toast>
      )}
    </>
  );
};

export default EditMenu;
