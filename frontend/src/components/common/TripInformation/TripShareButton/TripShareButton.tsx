import ShareIcon from '@assets/svg/share-icon.svg';
import { Flex, Menu, MenuList, useOverlay } from 'hang-log-design-system';
import { useEffect, useState } from 'react';
import type { ChangeEvent } from 'react';

import { useTripShareStatusMutation } from '@hooks/api/useTripShareStatusMutation';

import {
  copyButtonStyling,
  shareButtonStyling,
  shareContainerStyling,
  shareItemStyling,
  shareUrlStyling,
  shareUrlWrapperStyling,
  switchToggleStyling,
} from '@components/common/TripInformation/TripShareButton/TripShareButton.style';

interface TripShareButtonProps {
  tripId: number;
  sharedStatus: boolean;
}

const TripShareButton = ({ tripId, sharedStatus }: TripShareButtonProps) => {
  const patchTripSharedStatus = useTripShareStatusMutation();
  const [isSharable, setIsSharable] = useState(sharedStatus);
  const [shareUrl, setShareUrl] = useState<string | null>(null);
  const { isOpen: isShareMenuOpen, open: openShareMenu, close: closeShareMenu } = useOverlay();

  const communicateWithServer = (sharedStatus: boolean) => {
    patchTripSharedStatus.mutate(
      {
        tripId,
        sharedStatus,
      },
      {
        onSuccess: (sharedUrl: string | null) => {
          setShareUrl(sharedUrl);
        },
      }
    );
  };

  useEffect(() => {
    communicateWithServer(sharedStatus);
  }, []);

  const handleShareStateChange = (e: ChangeEvent<HTMLInputElement>) => {
    const isShared = e.target.checked;

    setIsSharable(isShared);

    communicateWithServer(isShared);
  };

  const handleCopyButtonClick = () => {
    navigator.clipboard
      .writeText(shareUrl ?? '')
      .then(() => {
        console.log('success');
      })
      .catch(() => {
        console.log('error');
      });
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
              <div css={shareUrlStyling}>{shareUrl}</div>
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
