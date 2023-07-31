import { CURRENCY_ICON } from '@constants/trip';
import type { TripItemData } from '@type/tripItem';
import { Box, Flex, Heading, ImageCarousel, Text, Theme } from 'hang-log-design-system';
import { useEffect, useRef } from 'react';

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
} from '@components/common/TripItem/TripItem.style';

interface TripListItemProps extends TripItemData {
  tripId: number;
  dayLogId: number;
  observer?: IntersectionObserver | null;
  onDragStart: () => void;
  onDragEnter: () => void;
  onDragEnd: () => void;
}

const TripItem = ({
  tripId,
  dayLogId,
  observer,
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
    // ! 수정 모드에서만 drag할 수 있다
    <li
      ref={itemRef}
      css={getContainerStyling(isDragging)}
      data-id={information.id}
      draggable
      onDragStart={onDragStart}
      onDrag={handleDrag}
      onDragEnter={onDragEnter}
      onDragEnd={handleDragEnd}
    >
      <Flex styles={{ gap: Theme.spacer.spacing4 }}>
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
      </Flex>
      {/* ! 로그인 + 수정 모드일 떄만 볼 수 있다 */}
      <EditMenu tripId={tripId} dayLogId={dayLogId} {...information} />
    </li>
  );
};

export default TripItem;
