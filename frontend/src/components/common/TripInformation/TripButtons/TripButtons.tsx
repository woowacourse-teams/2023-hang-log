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
import { useTrip } from '@hooks/trip/useTrip';

import type { TripData, TripTypeData } from '@type/trip';

import { PATH } from '@constants/path';
import { TRIP_TYPE } from '@constants/trip';

import BinIcon from '@assets/svg/bin-icon.svg';
import EditIcon from '@assets/svg/edit-icon.svg';

interface TripButtonsProps {
  tripType: TripTypeData;
  tripId: string;
  sharedCode: TripData['sharedCode'];
  publishState: boolean;
}

export const TripButtons = ({ tripType, tripId, sharedCode, publishState }: TripButtonsProps) => {
  const isPersonal = tripType === TRIP_TYPE.PERSONAL;
  const isPublished = tripType === TRIP_TYPE.PUBLISHED;

  const navigate = useNavigate();
  const deleteTripMutation = useDeleteTripMutation();

  const {
    tripData: { isWriter },
  } = useTrip(tripType, tripId);

  const {
    isOpen: isDeleteModalOpen,
    close: closeDeleteModal,
    open: openDeleteModal,
  } = useOverlay();

  const goToEditPage = () => {
    navigate(PATH.EDIT_TRIP(tripId));
  };

  const goToExpensePage = () => {
    if (tripType === TRIP_TYPE.PUBLISHED) {
      navigate(PATH.COMMUNITY_EXPENSE(tripId));
      return;
    }

    if (tripType === TRIP_TYPE.SHARED) {
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
      {isPersonal && (
        <>
          <TripShareButton tripId={tripId} sharedCode={sharedCode} publishState={publishState} />
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
      {isPublished && isWriter && (
        <Button type="button" variant="primary" size="small" onClick={goToEditPage}>
          수정하기
        </Button>
      )}
    </>
  );
};

export default TripButtons;
