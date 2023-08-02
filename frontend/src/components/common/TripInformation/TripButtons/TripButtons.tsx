import MoreIcon from '@assets/svg/more-icon.svg';
import ShareIcon from '@assets/svg/share-icon.svg';
import { PATH } from '@constants/path';
import { Button, Menu, MenuItem, MenuList, Toast, useOverlay } from 'hang-log-design-system';
import { useNavigate } from 'react-router-dom';

import { useDeleteTripMutation } from '@hooks/api/useDeleteTripMutation';

import {
  iconButtonStyling,
  moreButtonStyling,
  moreMenuListStyling,
  moreMenuStyling,
} from '@components/common/TripInformation/TripButtons/TripButtons.style';

interface TripButtonsProps {
  tripId: number;
}

export const TripButtons = ({ tripId }: TripButtonsProps) => {
  const navigate = useNavigate();
  const deleteTripMutation = useDeleteTripMutation();
  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();
  const { isOpen: isErrorTostOpen, open: openErrorToast, close: closeErrorToast } = useOverlay();

  const goToEditPage = () => {
    navigate(PATH.EDIT_TRIP(tripId));
  };

  const handleDeleteButtonClick = () => {
    deleteTripMutation.mutate(
      { tripId },
      {
        onSuccess: () => navigate(PATH.ROOT),
        onError: () => openErrorToast(),
      }
    );
  };

  return (
    <>
      <Button
        type="button"
        variant="primary"
        size="small"
        onClick={() => navigate(`/expense/${tripId}`)}
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
      <button css={iconButtonStyling} type="button">
        <MoreIcon />
      </button>
      {isErrorTostOpen && (
        <Toast variant="error" closeToast={closeErrorToast}>
          여행 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.
        </Toast>
      )}
    </>
  );
};

export default TripButtons;
