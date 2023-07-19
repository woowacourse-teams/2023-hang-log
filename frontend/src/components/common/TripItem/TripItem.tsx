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

import { moneyFormatter } from '@utils/formatter';

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
  onDragStart: () => void;
  onDragEnter: () => void;
  onDragEnd: () => void;
}

const TripItem = ({ onDragStart, onDragEnter, onDragEnd, ...information }: TripListItemProps) => {
  const { isOpen, open, close } = useOverlay();
  const [isDragging, setIsDragging] = useState(false);

  const handleDrag = () => {
    setIsDragging(true);
  };

  const handleDragEnd = () => {
    setIsDragging(false);
    onDragEnd();
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
              {moneyFormatter(information.expense.amount)}
            </Text>
          )}
        </Box>
      </Flex>
      {/* 로그인 + 수정 모드일 떄만 볼 수 있다 */}
      <Menu css={moreMenuStyling} closeMenu={close}>
        <button css={moreButtonStyling} type="button" onClick={open}>
          <MoreIcon />
        </button>
        {isOpen && (
          <MenuList css={moreMenuListStyling}>
            <MenuItem onClick={() => {}}>수정</MenuItem>
            <MenuItem onClick={() => {}}>삭제</MenuItem>
          </MenuList>
        )}
      </Menu>
    </li>
  );
};

export default TripItem;
