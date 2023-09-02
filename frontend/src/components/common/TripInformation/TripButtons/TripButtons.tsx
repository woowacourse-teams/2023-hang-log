import { useNavigate } from 'react-router-dom';

import { Button } from 'hang-log-design-system';

import {
  binIconStyling,
  editIconStyling,
  svgButtonStyling,
} from '@components/common/TripInformation/TripButtons/TripButtons.style';
import TripShareButton from '@components/common/TripInformation/TripShareButton/TripShareButton';

import { useDeleteTripMutation } from '@hooks/api/useDeleteTripMutation';

import type { TripData } from '@type/trip';

import { PATH } from '@constants/path';

import BinIcon from '@assets/svg/bin-icon.svg';
import EditIcon from '@assets/svg/edit-icon.svg';

interface TripButtonsProps {
  tripId: number;
  sharedCode: TripData['sharedCode'];
}

export const TripButtons = ({ tripId, sharedCode }: TripButtonsProps) => {
  const navigate = useNavigate();
  const deleteTripMutation = useDeleteTripMutation();

  const goToEditPage = () => {
    navigate(PATH.EDIT_TRIP(tripId));
  };

  const goToExpensePage = () => {
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
      <TripShareButton tripId={tripId} sharedCode={sharedCode} />
      <EditIcon css={[svgButtonStyling, editIconStyling]} onClick={goToEditPage} />
      <BinIcon css={[svgButtonStyling, binIconStyling]} onClick={handleDeleteButtonClick} />
    </>
  );
};

export default TripButtons;
