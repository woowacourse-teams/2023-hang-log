import type { ForwardedRef } from 'react';
import { useEffect, useRef } from 'react';

import { useRecoilValue } from 'recoil';

import { Box, Heading, ImageCarousel, Modal, Text, useOverlay } from 'hang-log-design-system';

import StarRating from '@components/common/StarRating/StarRating';
import EditMenu from '@components/common/TripItem/EditMenu/EditMenu';
import {
  contentContainerStyling,
  expenseStyling,
  getContainerStyling,
  informationContainerStyling,
  memoStyling,
  starRatingStyling,
  subInformationStyling,
  titleStyling,
} from '@components/common/TripItem/TripItem.style';

import { useDraggedItem } from '@hooks/common/useDraggedItem';
import useResizeImage from '@hooks/trip/useResizeImage';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { formatNumberToMoney } from '@utils/formatter';

import type { TripItemData } from '@type/tripItem';

import { CURRENCY_ICON } from '@constants/trip';
import { TRIP_ITEM_IMAGE_HEIGHT, TRIP_ITEM_IMAGE_WIDTH } from '@constants/ui';

interface TripListItemProps extends TripItemData {
  tripId: number;
  dayLogId: number;
  isEditable?: boolean;
  observer?: IntersectionObserver | null;
  scrollRef?: ForwardedRef<HTMLDivElement>;
  onDragStart?: () => void;
  onDragEnter?: () => void;
  onDragEnd?: () => void;
}

const TripItem = ({
  tripId,
  dayLogId,
  isEditable = true,
  observer,
  scrollRef,
  onDragStart,
  onDragEnter,
  onDragEnd,
  ...information
}: TripListItemProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);
  const { mobileImageSize } = useResizeImage({});

  const { isOpen: isImageModalOpen, open: openImageModal, close: closeImageModal } = useOverlay();

  const { isDragging, handleDrag, handleDragEnd } = useDraggedItem(onDragEnd);
  const itemRef = useRef<HTMLLIElement>(null);

  useEffect(() => {
    if (itemRef.current) {
      observer?.observe(itemRef.current);
    }
  }, [observer]);

  return (
    <>
      <li
        ref={itemRef}
        css={getContainerStyling({ isEditable, isDragging })}
        data-id={information.id}
        draggable={isEditable}
        onDragStart={onDragStart}
        onDrag={isEditable ? handleDrag : undefined}
        onDragEnter={onDragEnter}
        onDragEnd={isEditable ? handleDragEnd : undefined}
      >
        <div ref={scrollRef} css={contentContainerStyling}>
          {information.imageUrls.length > 0 && (
            // eslint-disable-next-line jsx-a11y/no-static-element-interactions
            <div
              onClick={isMobile ? undefined : openImageModal}
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  openImageModal();
                }
              }}
            >
              <ImageCarousel
                width={isMobile ? mobileImageSize.width : TRIP_ITEM_IMAGE_WIDTH}
                height={isMobile ? mobileImageSize.height : TRIP_ITEM_IMAGE_HEIGHT}
                isDraggable={false}
                showNavigationOnHover={!isMobile}
                showArrows={information.imageUrls.length > 1}
                showDots={information.imageUrls.length > 1}
                images={information.imageUrls}
              />
            </div>
          )}
          <Box tag="section" css={informationContainerStyling}>
            <Text size="large" css={titleStyling}>
              {information.title}
            </Text>
            {information.place && (
              <Text css={subInformationStyling} size="small">
                {information.place.category.name}
              </Text>
            )}
            {information.rating && <StarRating css={starRatingStyling} rate={information.rating} />}
            {information.memo && (
              <Text css={memoStyling} size="small">
                {information.memo}
              </Text>
            )}
            {information.expense && (
              <Text css={expenseStyling} size="small">
                {information.expense.category.name} · {CURRENCY_ICON[information.expense.currency]}
                {formatNumberToMoney(information.expense.amount)}
              </Text>
            )}
          </Box>
        </div>
        {isEditable ? (
          <EditMenu
            tripId={tripId}
            dayLogId={dayLogId}
            hasImage={information.imageUrls.length > 0}
            imageHeight={mobileImageSize.height}
            {...information}
          />
        ) : null}
      </li>
      <Modal isOpen={isImageModalOpen} closeModal={closeImageModal}>
        <Heading size="small">{information.title}</Heading>
        <ImageCarousel
          width={TRIP_ITEM_IMAGE_WIDTH * 2}
          height={TRIP_ITEM_IMAGE_HEIGHT * 2}
          isDraggable={false}
          showNavigationOnHover={!isMobile}
          showArrows={information.imageUrls.length > 1}
          showDots={information.imageUrls.length > 1}
          images={information.imageUrls}
        />
      </Modal>
    </>
  );
};

export default TripItem;
