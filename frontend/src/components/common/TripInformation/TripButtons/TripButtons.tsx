import MoreIcon from '@assets/svg/more-icon.svg';
import { PATH } from '@constants/path';
import type { TripData } from '@type/trip';
import { Button, Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';
import { useNavigate } from 'react-router-dom';

import { useDeleteTripMutation } from '@hooks/api/useDeleteTripMutation';

import {
  moreButtonStyling,
  moreMenuListStyling,
  moreMenuStyling,
} from '@components/common/TripInformation/TripButtons/TripButtons.style';
import TripShareButton from '@components/common/TripInformation/TripShareButton/TripShareButton';

interface TripButtonsProps {
  tripId: number;
  sharedCode: TripData['sharedCode'];
}

export const TripButtons = ({ tripId, sharedCode }: TripButtonsProps) => {
  const navigate = useNavigate();
  const deleteTripMutation = useDeleteTripMutation();
  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();

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
      <Menu css={moreMenuStyling} closeMenu={closeMenu}>
        <button css={moreButtonStyling} type="button" aria-label="더 보기 메뉴" onClick={openMenu}>
          <MoreIcon />
        </button>
        {isMenuOpen && (
          <MenuList css={moreMenuListStyling}>
            <MenuItem onClick={goToEditPage}>수정</MenuItem>
            <MenuItem onClick={handleDeleteButtonClick}>삭제</MenuItem>
          </MenuList>
        )}
      </Menu>
    </>
  );
};

export default TripButtons;
