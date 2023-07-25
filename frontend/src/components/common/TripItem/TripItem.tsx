import TripItemAddModal from '@/components/trip/TripItemAddModal/TripItemAddModal';
import { useTripDates } from '@/hooks/trip/useTripDates';
import MoreIcon from '@assets/svg/more-icon.svg';
import { CURRENCY_ICON } from '@constants/trip';
import type { TripItemData } from '@type/tripItem';
import {
  Box,
  Flex,
  Heading,
  ImageCarousel,
  Menu,
  MenuItem,
  MenuList,
  Text,
  Theme,
  useOverlay,
} from 'hang-log-design-system';
import { useState } from 'react';

import { formatNumberToMoney } from '@utils/formatter';

import { useDeleteTripItemMutation } from '@hooks/api/useDeleteTripItemMutation';

import StarRating from '@components/common/StarRating/StarRating';
import {
  expenseStyling,
  getContainerStyling,
  informationContainerStyling,
  memoStyling,
  moreButtonStyling,
  moreMenuListStyling,
  moreMenuStyling,
  starRatingStyling,
  subInformationStyling,
} from '@components/common/TripItem/TripItem.style';

interface TripListItemProps extends TripItemData {
  tripId: number;
  dayLogId: number;
  onDragStart: () => void;
  onDragEnter: () => void;
  onDragEnd: () => void;
}

const TripItem = ({
  tripId,
  dayLogId,
  onDragStart,
  onDragEnter,
  onDragEnd,
  ...information
}: TripListItemProps) => {
  const deleteTripItemMutation = useDeleteTripItemMutation();
  const { tripDates } = useTripDates(tripId);
  const { isOpen: isMenuOpen, open: openMenu, close: closeMenu } = useOverlay();
  const { isOpen: isModalOpen, open: openModal, close: closeModal } = useOverlay();
  const [isDragging, setIsDragging] = useState(false);

  const handleDrag = () => {
    setIsDragging(true);
  };

  const handleDragEnd = () => {
    setIsDragging(false);
    onDragEnd();
  };

  const handleTripItem = () => {
    deleteTripItemMutation.mutate({ tripId, itemId: information.id });
  };

  return (
    // 수정 모드에서만 drag할 수 있다
    <li
      css={getContainerStyling(isDragging)}
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
            showNavigationOnHover={true}
            showArrows={true}
            showDots={true}
            images={information.imageUrls}
          />
        )}
        <Box css={informationContainerStyling}>
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
      {/* 로그인 + 수정 모드일 떄만 볼 수 있다 */}
      <Menu css={moreMenuStyling} closeMenu={closeMenu}>
        <button css={moreButtonStyling} type="button" onClick={openMenu}>
          <MoreIcon />
        </button>
        {isMenuOpen && (
          <MenuList css={moreMenuListStyling}>
            <MenuItem onClick={openModal}>수정</MenuItem>
            <MenuItem onClick={handleTripItem}>삭제</MenuItem>
          </MenuList>
        )}
      </Menu>
      <TripItemAddModal
        tripId={tripId}
        itemId={information.id}
        dayLogId={dayLogId}
        isOpen={isModalOpen}
        dates={tripDates}
        onClose={closeModal}
        initialData={{
          itemType: information.itemType,
          dayLogId,
          title: information.title,
          isPlaceUpdated: false,
          place: null,
          rating: information.rating,
          expense: information.expense
            ? {
                currency: information.expense.currency,
                amount: information.expense.amount,
                categoryId: information.expense.category.id,
              }
            : null,
          memo: information.memo,
          imageUrls: information.imageUrls,
        }}
      />
    </li>
  );
};

export default TripItem;
