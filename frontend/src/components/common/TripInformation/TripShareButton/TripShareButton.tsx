import { useState } from 'react';
import type { ChangeEvent } from 'react';

import { Flex, Menu, MenuList, SwitchToggle, useOverlay } from 'hang-log-design-system';

import {
  copyButtonStyling,
  shareButtonStyling,
  shareContainerStyling,
  shareItemStyling,
  shareUrlStyling,
  shareUrlWrapperStyling,
} from '@components/common/TripInformation/TripShareButton/TripShareButton.style';

import { useTripShareStatusMutation } from '@hooks/api/useTripShareStatusMutation';
import { useTripShare } from '@hooks/trip/useTripShare';

import type { TripData } from '@type/trip';

import { BASE_URL } from '@constants/api';
import { PATH } from '@constants/path';

import ShareIcon from '@assets/svg/share-icon.svg';

interface TripShareButtonProps {
  tripId: number;
  sharedCode: TripData['sharedCode'];
}

const TripShareButton = ({ tripId, sharedCode }: TripShareButtonProps) => {
  const tripShareStatusMutation = useTripShareStatusMutation();
  const [isSharable, setIsSharable] = useState(!!sharedCode);
  const [sharedUrl, setShareUrl] = useState(
    sharedCode ? BASE_URL + PATH.SHARE_TRIP(sharedCode) : null
  );
  const { isOpen: isShareMenuOpen, open: openShareMenu, close: closeShareMenu } = useOverlay();
  const { handleCopyButtonClick } = useTripShare(sharedUrl);

  const handleShareStateChange = (e: ChangeEvent<HTMLInputElement>) => {
    const isChecked = e.target.checked;

    tripShareStatusMutation.mutate(
      {
        tripId,
        sharedStatus: isChecked,
      },
      {
        onSuccess: (sharedCode: string | null) => {
          setIsSharable(isChecked);

          if (!sharedCode || !!sharedUrl) return;

          setShareUrl(BASE_URL + PATH.SHARE_TRIP(sharedCode));
        },
      }
    );
  };

  return (
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
            <SwitchToggle checkedState={isSharable} onChange={handleShareStateChange} />
          </div>
          {isSharable && (
            <Flex css={shareUrlWrapperStyling}>
              <div css={shareUrlStyling}>{sharedUrl}</div>
              <button type="button" onClick={handleCopyButtonClick} css={copyButtonStyling}>
                링크 복사
              </button>
            </Flex>
          )}
        </MenuList>
      )}
    </Menu>
  );
};

export default TripShareButton;
