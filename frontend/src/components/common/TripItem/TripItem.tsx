import type { ForwardedRef } from 'react';
import { useEffect, useRef } from 'react';

import { useRecoilValue } from 'recoil';

import { Box, Carousel, ImageCarousel, Modal, Text, useOverlay } from 'hang-log-design-system';

import StarRating from '@components/common/StarRating/StarRating';
import EditMenu from '@components/common/TripItem/EditMenu/EditMenu';
import {
  contentContainerStyling,
  expandedImage,
  expandedImageContainer,
  expenseStyling,
  getContainerStyling,
  imageModalStyling,
  informationContainerStyling,
  memoStyling,
  starRatingStyling,
  subInformationStyling,
  titleStyling,
} from '@components/common/TripItem/TripItem.style';

import { useDraggedItem } from '@hooks/common/useDraggedItem';
import useResizeImage from '@hooks/trip/useResizeImage';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { convertToImageUrl, convertToImageUrls } from '@utils/convertImage';
import { formatNumberToMoney } from '@utils/formatter';

import type { TripItemData } from '@type/tripItem';

import { CURRENCY_ICON } from '@constants/trip';
import { TRIP_ITEM_IMAGE_HEIGHT, TRIP_ITEM_IMAGE_WIDTH } from '@constants/ui';

interface TripListItemProps extends TripItemData {
  tripId: string;
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
  const { width, height } = useResizeImage();

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
          {information.imageNames.length > 0 && (
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
                width={isMobile ? width : TRIP_ITEM_IMAGE_WIDTH}
                height={isMobile ? height : TRIP_ITEM_IMAGE_HEIGHT}
                isDraggable={false}
                showNavigationOnHover={!isMobile}
                showArrows={information.imageNames.length > 1}
                showDots={information.imageNames.length > 1}
                images={convertToImageUrls(information.imageNames)}
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
            hasImage={information.imageNames.length > 0}
            imageHeight={height}
            {...information}
          />
        ) : null}
      </li>
      {!isMobile && (
        <Modal isOpen={isImageModalOpen} closeModal={closeImageModal} css={imageModalStyling}>
          <Carousel
            width={800}
            height={500}
            isDraggable={false}
            showNavigationOnHover={!isMobile}
            showArrows={information.imageNames.length > 1}
            showDots={information.imageNames.length > 1}
            images={convertToImageUrls(information.imageNames)}
          >
            {information.imageNames.map((imageName) => (
              <div css={expandedImageContainer}>
                <img src={convertToImageUrl(imageName)} alt="이미지" css={expandedImage} />
              </div>
            ))}
          </Carousel>
        </Modal>
      )}
    </>
  );
};

export default TripItem;
