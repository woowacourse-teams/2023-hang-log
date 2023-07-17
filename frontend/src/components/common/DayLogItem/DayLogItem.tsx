import { DAY_LOG_ITEM_FILTERS } from '@constants/trip';
import type { DayLogData } from '@type/dayLog';
import { Box, Flex, Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';
import { useEffect } from 'react';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TitleInput from '@components/common/DayLogItem/TitleInput/TitleInput';
import TripItemList from '@components/common/TripItemList/TripItemList';

interface DayLogItemProps extends DayLogData {
  tripId: number;
}

const DayLogItem = ({ tripId, ...information }: DayLogItemProps) => {
  const { selected: selectedFilter, handleSelectClick: handleFilterSelectClick } = useSelect(
    DAY_LOG_ITEM_FILTERS.ALL
  );
  const selectedTripItemList =
    selectedFilter === DAY_LOG_ITEM_FILTERS.SPOT
      ? information.items.filter((item) => item.itemType === true)
      : information.items;

  useEffect(() => {
    /** ordinal 변경되었을 때 목록 및 선택된 토클 초기화 */
    handleFilterSelectClick(DAY_LOG_ITEM_FILTERS.ALL);
  }, [information.items]);

  return (
    <Box css={containerStyling}>
      <Flex css={headerStyling} styles={{ justify: 'space-between' }}>
        {/* 수정 모드일 때만 보인다 */}
        <TitleInput initialTitle={information.title} />
        <ToggleGroup>
          <Toggle
            text={DAY_LOG_ITEM_FILTERS.ALL}
            toggleId={DAY_LOG_ITEM_FILTERS.ALL}
            selectedId={selectedFilter}
            changeSelect={handleFilterSelectClick}
          />
          <Toggle
            text={DAY_LOG_ITEM_FILTERS.SPOT}
            toggleId={DAY_LOG_ITEM_FILTERS.SPOT}
            selectedId={selectedFilter}
            changeSelect={handleFilterSelectClick}
          />
        </ToggleGroup>
      </Flex>
      {selectedTripItemList.length > 0 ? (
        <TripItemList tripId={tripId} dayLogId={information.id} tripItems={selectedTripItemList} />
      ) : (
        <TripItemList.Empty />
      )}
    </Box>
  );
};

export default DayLogItem;
