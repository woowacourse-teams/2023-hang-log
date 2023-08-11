import ShareIcon from '@assets/svg/share-icon.svg';
import { BASE_URL } from '@constants/api';
import { PATH } from '@constants/path';
import { toastListState } from '@store/toast';
import type { TripData } from '@type/trip';
import { Flex, Menu, MenuList, SwitchToggle, useOverlay } from 'hang-log-design-system';
import { useState } from 'react';
import type { ChangeEvent } from 'react';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import { useTripShareStatusMutation } from '@hooks/api/useTripShareStatusMutation';

import {
  copyButtonStyling,
  shareButtonStyling,
  shareContainerStyling,
  shareItemStyling,
  shareUrlStyling,
  shareUrlWrapperStyling,
} from '@components/common/TripInformation/TripShareButton/TripShareButton.style';

interface TripShareButtonProps {
  tripId: number;
  sharedCode: TripData['sharedCode'];
}

const TripShareButton = ({ tripId, sharedCode }: TripShareButtonProps) => {
  const tripShareStatusMutation = useTripShareStatusMutation();
  const [isSharable, setIsSharable] = useState(!!sharedCode);
  const [shareUrl, setShareUrl] = useState(sharedCode ? BASE_URL + PATH.SHARE(sharedCode) : '');
  const { isOpen: isShareMenuOpen, open: openShareMenu, close: closeShareMenu } = useOverlay();
  const setToastList = useSetRecoilState(toastListState);

  const handleShareStateChange = (e: ChangeEvent<HTMLInputElement>) => {
    const isChecked = e.target.checked;

    setIsSharable(isChecked);

    tripShareStatusMutation.mutate(
      {
        tripId,
        sharedStatus: isChecked,
      },
      {
        onSuccess: (sharedCode: string | null) => {
          if (!sharedCode || !!shareUrl) return;

          setShareUrl(BASE_URL + PATH.SHARE(sharedCode));
        },
      }
    );
  };

  const handleCopyButtonClick = () => {
    if (!shareUrl) return;

    navigator.clipboard
      .writeText(shareUrl)
      .then(() => {
        setToastList((prevToastList) => [
          ...prevToastList,
          {
            id: generateUniqueId(),
            variant: 'default',
            message: '공유 링크가 클립보드에 복사되었습니다.',
          },
        ]);
      })
      .catch(() => {
        setToastList((prevToastList) => [
          ...prevToastList,
          {
            id: generateUniqueId(),
            variant: 'error',
            message: '복사에 실패했습니다. 잠시 후 다시 시도해주세요.',
          },
        ]);
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
            <SwitchToggle checkedState={isSharable} onChange={handleShareStateChange} />
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
