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
import { memo } from 'react';

import { moneyFormatter } from '@utils/formatter';

import StarRating from '@components/common/StarRating/StarRating';
import {
  containerStyling,
  informationContainerStyling,
  memoStyling,
  moreButtonStyling,
  moreMenuListStyling,
  moreMenuStyling,
  starRatingStyling,
  subInformationStyling,
} from '@components/common/TripItem/TripItem.style';

type TripListItemProps = TripItemData;

const TripItem = ({ ...information }: TripListItemProps) => {
  const { isOpen, open, close } = useOverlay();

  return (
    <li css={containerStyling}>
      <Flex styles={{ gap: Theme.spacer.spacing4 }}>
        {information.imageUrls && (
          <ImageCarousel
            width={250}
            height={167}
            showNavigationOnHover={true}
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
            <Text size="small" css={memoStyling}>
              {information.memo}
            </Text>
          )}
          {information.expense && (
            <Text size="small">
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
            <MenuItem onClick={() => console.log('수정')}>마이페이지</MenuItem>
            <MenuItem onClick={() => console.log('삭제')}>로그아웃</MenuItem>
          </MenuList>
        )}
      </Menu>
    </li>
  );
};

export default memo(TripItem);
