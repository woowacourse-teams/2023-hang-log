import MoreIcon from '@assets/svg/more-icon.svg';
import ShareIcon from '@assets/svg/share-icon.svg';
import { Button, Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';
import { useNavigate } from 'react-router-dom';

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
  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();

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
            <MenuItem onClick={() => navigate(`/trip-edit/${tripId}`)}>수정</MenuItem>
            {/* TODO : 삭제 기능 추가! */}
            <MenuItem onClick={() => {}}>삭제</MenuItem>
          </MenuList>
        )}
      </Menu>
      <button css={iconButtonStyling} type="button">
        <MoreIcon />
      </button>
    </>
  );
};

export default TripButtons;
