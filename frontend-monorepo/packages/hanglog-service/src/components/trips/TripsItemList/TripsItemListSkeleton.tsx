import { Box, Flex, NewToggle as Toggle } from 'hang-log-design-system';

import TripsItemSkeleton from '@components/trips/TripsItem/TripsItemSkeleton';
import {
  gridBoxStyling,
  toggleGroupStyling,
} from '@components/trips/TripsItemList/TripsItemList.style';

import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';

const TripsItemListSkeleton = () => {
  return (
    <Toggle initialSelect={ORDER_BY_REGISTRATION}>
      <Flex
        tag="section"
        styles={{ justify: 'right', paddingRight: '50px' }}
        css={toggleGroupStyling}
      >
        <Toggle.List text={ORDER_BY_REGISTRATION} toggleKey={ORDER_BY_REGISTRATION} />
        <Toggle.List text={ORDER_BY_DATE} toggleKey={ORDER_BY_DATE} />
      </Flex>
      <Box tag="ol" css={gridBoxStyling}>
        {Array.from({ length: 10 }, (_, index) => {
          return <TripsItemSkeleton key={index} />;
        })}
      </Box>
    </Toggle>
  );
};

export default TripsItemListSkeleton;
