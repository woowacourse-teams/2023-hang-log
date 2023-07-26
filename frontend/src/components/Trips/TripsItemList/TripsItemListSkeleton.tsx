import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import { Box, Flex, Toggle, ToggleGroup } from 'hang-log-design-system';

import {
  gridBoxStyling,
  toggleGroupStyling,
} from '@components/trips/TripsItemList/TripsItemList.style';

import TripsItemSkeleton from '../TripsItem/TripsItemSkeleton';

const TripsItemListSkeleton = () => {
  return (
    <>
      <Flex
        tag="section"
        styles={{ justify: 'right', paddingRight: '50px' }}
        css={toggleGroupStyling}
      >
        <ToggleGroup>
          <Toggle
            text={ORDER_BY_REGISTRATION}
            toggleId={ORDER_BY_REGISTRATION}
            selectedId="등록순"
            changeSelect={() => {}}
          />
          <Toggle
            text={ORDER_BY_DATE}
            toggleId={ORDER_BY_DATE}
            selectedId="등록순"
            changeSelect={() => {}}
          />
        </ToggleGroup>
      </Flex>
      <Box tag="ol" css={gridBoxStyling}>
        {Array.from({ length: 10 }, () => {
          return <TripsItemSkeleton />;
        })}
      </Box>
    </>
  );
};

export default TripsItemListSkeleton;
