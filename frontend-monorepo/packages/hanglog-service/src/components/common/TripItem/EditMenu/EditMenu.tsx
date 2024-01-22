import { Box, Button, Flex, Heading, Modal, Text, useOverlay } from 'hang-log-design-system';

import {
  binIconStyling,
  editIconStyling,
  getEditMenuStyling,
  modalButtonContainerStyling,
  modalContentStyling,
} from '@components/common/TripItem/EditMenu/EditMenu.style';
import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

import { useDeleteTripItemMutation } from '@hooks/api/useDeleteTripItemMutation';

import type { TripItemData } from '@type/tripItem';

import BinIcon from '@assets/svg/bin-icon.svg';
import EditIcon from '@assets/svg/edit-icon.svg';

interface EditMenuProps extends TripItemData {
  tripId: string;
  dayLogId: number;
  hasImage: boolean;
  imageHeight: number;
}

const EditMenu = ({ tripId, dayLogId, hasImage, imageHeight, ...information }: EditMenuProps) => {
  const deleteTripItemMutation = useDeleteTripItemMutation();

  const { isOpen: isEditModalOpen, open: openEditModal, close: closeEditModal } = useOverlay();
  const {
    isOpen: isDeleteModalOpen,
    close: closeDeleteModal,
    open: openDeleteModal,
  } = useOverlay();

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
        <BinIcon css={binIconStyling} role="button" aria-label="삭제" onClick={openDeleteModal} />
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
            imageNames: information.imageNames,
          }}
        />
      )}
      <Modal isOpen={isDeleteModalOpen} closeModal={closeDeleteModal}>
        <Box css={modalContentStyling}>
          <Heading size="xSmall">여행 기록을 삭제하겠어요?</Heading>
          <Text>여행 기록을 한번 삭제하면 다시 복구하기는 힘들어요.</Text>
          <Flex css={modalButtonContainerStyling}>
            <Button variant="default" onClick={closeDeleteModal}>
              취소
            </Button>
            <Button variant="danger" onClick={handleTripItemDelete}>
              삭제
            </Button>
          </Flex>
        </Box>
      </Modal>
    </>
  );
};

export default EditMenu;
