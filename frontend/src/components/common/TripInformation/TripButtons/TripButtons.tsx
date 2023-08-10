import MoreIcon from '@assets/svg/more-icon.svg';
import ShareIcon from '@assets/svg/share-icon.svg';
import { PATH } from '@constants/path';
import { Button, Flex, Menu, MenuItem, MenuList, useOverlay } from 'hang-log-design-system';
import { useState } from 'react';
import type { ChangeEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import { useDeleteTripMutation } from '@hooks/api/useDeleteTripMutation';

import {
  copyButtonStyling,
  moreButtonStyling,
  moreMenuListStyling,
  moreMenuStyling,
  shareButtonStyling,
  shareContainerStyling,
  shareItemStyling,
  shareUrlStyling,
  shareUrlWrapperStyling,
  switchToggleStyling,
} from '@components/common/TripInformation/TripButtons/TripButtons.style';

interface TripButtonsProps {
  tripId: number;

  sharedStatus: boolean;
}

export const TripButtons = ({ tripId, sharedStatus }: TripButtonsProps) => {
  const navigate = useNavigate();
  const deleteTripMutation = useDeleteTripMutation();
  const [isSharable, setIsSharable] = useState(sharedStatus);
  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();
  const { isOpen: isShareMenuOpen, open: openShareMenu, close: closeShareMenu } = useOverlay();

  const goToEditPage = () => {
    navigate(PATH.EDIT_TRIP(tripId));
  };

  const goToExpensePage = () => {
    navigate(PATH.EXPENSE(tripId));
  };

  const handleShareStateChange = (e: ChangeEvent<HTMLInputElement>) => {
    setIsSharable(e.target.checked);
  };

  const handleCopyButtonClick = () => {
    navigator.clipboard
      .writeText('sdhfkjsd')
      .then(() => {
        console.log('success');
      })
      .catch(() => {
        console.log('error');
      });
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
      <Menu closeMenu={closeShareMenu}>
        <button
          onClick={openShareMenu}
          type="button"
          aria-label="공유하기 버튼"
          css={shareButtonStyling}
        >
          <ShareIcon />
        </button>
        {isShareMenuOpen && (
          <MenuList css={shareContainerStyling}>
            <div css={shareItemStyling}>
              <span css={{ color: 'black' }}>여행 공유</span>
              <input
                type="checkbox"
                role="switch"
                aria-checked={isSharable}
                checked={isSharable}
                onChange={handleShareStateChange}
                css={switchToggleStyling}
              />
            </div>
            {isSharable && (
              <Flex css={shareUrlWrapperStyling}>
                <div css={shareUrlStyling}>hanglog.com/trip/1/share/:code</div>
                <button type="button" onClick={handleCopyButtonClick} css={copyButtonStyling}>
                  링크 복사
                </button>
              </Flex>
            )}
          </MenuList>
        )}
      </Menu>
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
