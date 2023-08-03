import { CURRENCY_ICON } from '@constants/trip';
import type { TripItemData } from '@type/tripItem';
import { Box, Heading, ImageCarousel, Text } from 'hang-log-design-system';
import { useEffect, useRef } from 'react';
import type { ForwardedRef } from 'react';

import { formatNumberToMoney } from '@utils/formatter';

import { useDraggedItem } from '@hooks/common/useDraggedItem';

import StarRating from '@components/common/StarRating/StarRating';
import EditMenu from '@components/common/TripItem/EditMenu/EditMenu';
import {
  expenseStyling,
  getContainerStyling,
  informationContainerStyling,
  memoStyling,
  starRatingStyling,
  subInformationStyling,
  wrapperStyling,
} from '@components/common/TripItem/TripItem.style';

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
  const { isDragging, handleDrag, handleDragEnd } = useDraggedItem(onDragEnd);
  const itemRef = useRef<HTMLLIElement>(null);

  useEffect(() => {
    if (itemRef.current) {
      observer?.observe(itemRef.current);
    }
  }, [observer]);

  return (
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
      <div ref={scrollRef} css={wrapperStyling}>
        {information.imageUrls.length > 0 && (
          <ImageCarousel
            width={250}
            height={167}
            isDraggable={false}
            showNavigationOnHover
            showArrows
            showDots
            images={information.imageUrls}
          />
        )}
        <Box tag="section" css={informationContainerStyling}>
          <Heading size="xSmall">{information.title}</Heading>
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
      {isEditable ? <EditMenu tripId={tripId} dayLogId={dayLogId} {...information} /> : null}
    </li>
  );
};

export default TripItem;
