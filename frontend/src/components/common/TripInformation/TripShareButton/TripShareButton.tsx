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

import { useTripPublishStatusMutation } from '@hooks/api/useTripPublishMutation';
import { useTripShareStatusMutation } from '@hooks/api/useTripShareStatusMutation';
import { useTripShare } from '@hooks/trip/useTripShare';

import type { TripData } from '@type/trip';

import { BASE_URL } from '@constants/api';
import { PATH } from '@constants/path';

import ShareIcon from '@assets/svg/share-icon.svg';

interface TripShareButtonProps {
  tripId: string;
  sharedCode: TripData['sharedCode'];
  isPublished: boolean;
}

const TripShareButton = ({
  tripId,
  sharedCode,
  isPublished: initialPublishState,
}: TripShareButtonProps) => {
  const tripShareStatusMutation = useTripShareStatusMutation();
  const tripPublishStatusMutation = useTripPublishStatusMutation();

  const [isSharable, setIsSharable] = useState(!!sharedCode);
  const [sharedUrl, setShareUrl] = useState(
    sharedCode ? BASE_URL + PATH.SHARE_TRIP(sharedCode) : null
  );
  const [isPublished, setIsPublished] = useState(initialPublishState);

  const { isOpen: isShareMenuOpen, close: closeShareMenu, toggle: toggleShareMenu } = useOverlay();
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

  const handlePublishStatusChange = (e: ChangeEvent<HTMLInputElement>) => {
    const isChecked = e.target.checked;

    tripPublishStatusMutation.mutate(
      {
        tripId,
        publishedStatus: isChecked,
      },
      {
        onSuccess: () => {
          setIsPublished(isChecked);
        },
      }
    );
  };

  return (
    <Menu closeMenu={closeShareMenu}>
      <button
        onClick={toggleShareMenu}
        type="button"
        aria-label="공유하기 버튼"
        css={shareButtonStyling}
      >
        <ShareIcon />
      </button>
      {isShareMenuOpen && (
        <MenuList css={shareContainerStyling}>
          <div css={shareItemStyling}>
            <span css={{ color: 'black' }}>커뮤니티 공개</span>
            <SwitchToggle checkedState={isPublished} onChange={handlePublishStatusChange} />
          </div>
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
