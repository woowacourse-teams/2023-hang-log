import { useNavigate } from 'react-router-dom';

import { Button, Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';

import {
  iconButtonStyling,
  moreButtonStyling,
  moreMenuListStyling,
  moreMenuStyling,
} from '@components/common/TripInformation/TripButtons/TripButtons.style';

import { useDeleteTripMutation } from '@hooks/api/useDeleteTripMutation';

import { PATH } from '@constants/path';

import MoreIcon from '@assets/svg/more-icon.svg';
import ShareIcon from '@assets/svg/share-icon.svg';

interface TripButtonsProps {
  tripId: number;
}

export const TripButtons = ({ tripId }: TripButtonsProps) => {
  const navigate = useNavigate();
  const deleteTripMutation = useDeleteTripMutation();
  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();

  const goToEditPage = () => {
    navigate(PATH.EDIT_TRIP(tripId));
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
      <Button
        type="button"
        variant="primary"
        size="small"
        onClick={() => navigate(PATH.EXPENSE(tripId))}
      >
        가계부
      </Button>
      <button css={iconButtonStyling} type="button">
        <ShareIcon />
      </button>
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
