import { CURRENCY_ICON } from '@constants/trip';
import type { TripItemData } from '@type/tripItem';
import { Box, Flex, Heading, ImageCarousel, Text, Theme } from 'hang-log-design-system';

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
  isEditable?: boolean;
  onDragStart?: () => void;
  onDragEnter?: () => void;
  onDragEnd?: () => void;
}

const TripItem = ({
  tripId,
  dayLogId,
  isEditable = true,
  onDragStart,
  onDragEnter,
  onDragEnd,
  ...information
}: TripListItemProps) => {
  const { isDragging, handleDrag, handleDragEnd } = useDraggedItem(onDragEnd);

  return (
    // ! 수정 모드에서만 drag할 수 있다
    <li
      css={getContainerStyling({ isEditable, isDragging })}
      draggable={isEditable}
      onDragStart={onDragStart}
      onDrag={isEditable ? handleDrag : undefined}
      onDragEnter={onDragEnter}
      onDragEnd={isEditable ? handleDragEnd : undefined}
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
      {isEditable ? <EditMenu tripId={tripId} dayLogId={dayLogId} {...information} /> : null}
    </li>
  );
};

export default TripItem;
