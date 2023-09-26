import { useNavigate } from 'react-router-dom';

import { Box, Button, Flex, Heading, Modal, Text, useOverlay } from 'hang-log-design-system';

import {
  binIconStyling,
  editIconStyling,
  modalButtonContainerStyling,
  modalContentStyling,
  svgButtonStyling,
} from '@components/common/TripInformation/TripButtons/TripButtons.style';
import TripShareButton from '@components/common/TripInformation/TripShareButton/TripShareButton';

import { useDeleteTripMutation } from '@hooks/api/useDeleteTripMutation';

import type { TripData } from '@type/trip';

import { PATH } from '@constants/path';

import BinIcon from '@assets/svg/bin-icon.svg';
import EditIcon from '@assets/svg/edit-icon.svg';

interface TripButtonsProps {
  tripId: string;
  sharedCode: TripData['sharedCode'];
  isShared: boolean;
  isPublished?: boolean;
}

export const TripButtons = ({
  tripId,
  sharedCode,
  isShared,
  isPublished = false,
}: TripButtonsProps) => {
  const navigate = useNavigate();
  const deleteTripMutation = useDeleteTripMutation();

  const {
    isOpen: isDeleteModalOpen,
    close: closeDeleteModal,
    open: openDeleteModal,
  } = useOverlay();

  const goToEditPage = () => {
    navigate(PATH.EDIT_TRIP(tripId));
  };

  const goToExpensePage = () => {
    if (isPublished) {
      navigate(PATH.COMMUNITY_TRIP(tripId));
      return;
    }

    if (isShared) {
      navigate(PATH.SHARE_EXPENSE(tripId));
      return;
    }

    navigate(PATH.EXPENSE(tripId));
  };

  const handleDeleteButtonClick = () => {
    deleteTripMutation.mutate(
      { tripId },
      {
        onSuccess: () => navigate(PATH.ROOT),
      }
    );
  };

  return (
    <>
      <Button type="button" variant="primary" size="small" onClick={goToExpensePage}>
        가계부
      </Button>
      {!isShared && (
        <>
          <TripShareButton tripId={tripId} sharedCode={sharedCode} isPublished={isPublished} />
          <EditIcon css={[svgButtonStyling, editIconStyling]} onClick={goToEditPage} />
          <BinIcon css={[svgButtonStyling, binIconStyling]} onClick={openDeleteModal} />
          <Modal isOpen={isDeleteModalOpen} closeModal={closeDeleteModal}>
            <Box css={modalContentStyling}>
              <Heading size="xSmall">여행 아이템을 삭제하겠어요?</Heading>
              <Text>여행 아이템을 한번 삭제하면 다시 복구하기는 힘들어요.</Text>
              <Flex css={modalButtonContainerStyling}>
                <Button variant="default" onClick={closeDeleteModal}>
                  취소
                </Button>
                <Button variant="danger" onClick={handleDeleteButtonClick}>
                  삭제
                </Button>
              </Flex>
            </Box>
          </Modal>
        </>
      )}
    </>
  );
};

export default TripButtons;
