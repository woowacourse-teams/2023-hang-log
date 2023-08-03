import { DAY_LOG_ITEM_FILTERS } from '@constants/trip';
import type { DayLogData } from '@type/dayLog';
import { Box, Flex, Heading, Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';
import { useEffect } from 'react';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TitleInput from '@components/common/DayLogItem/TitleInput/TitleInput';
import TripItemList from '@components/common/TripItemList/TripItemList';

interface DayLogItemProps extends DayLogData {
  tripId: number;
  isEditable?: boolean;
  openAddModal?: () => void;
}

const DayLogItem = ({
  tripId,
  isEditable = true,
  openAddModal,
  ...information
}: DayLogItemProps) => {
  const { selected: selectedFilter, handleSelectClick: handleFilterSelectClick } = useSelect(
    DAY_LOG_ITEM_FILTERS.ALL
  );
  const selectedTripItemList =
    selectedFilter === DAY_LOG_ITEM_FILTERS.SPOT
      ? information.items.filter((item) => item.itemType === true)
      : information.items;

  useEffect(() => {
    // ! 선택된 날짜 탭이 변경되었을 때 선택된 필터 초기화
    handleFilterSelectClick(DAY_LOG_ITEM_FILTERS.ALL);
  }, [handleFilterSelectClick, information.items]);

  return (
    <Box tag="section" css={containerStyling}>
      <Flex css={headerStyling} styles={{ justify: 'space-between' }}>
        {isEditable ? (
          <TitleInput tripId={tripId} dayLogId={information.id} initialTitle={information.title} />
        ) : (
          <Heading size="xSmall">{information.title}</Heading>
        )}
        <ToggleGroup>
          <Toggle
            text={DAY_LOG_ITEM_FILTERS.ALL}
            toggleId={DAY_LOG_ITEM_FILTERS.ALL}
            selectedId={selectedFilter}
            changeSelect={handleFilterSelectClick}
            aria-label={`${DAY_LOG_ITEM_FILTERS.ALL} 필터`}
          />
          <Toggle
            text={DAY_LOG_ITEM_FILTERS.SPOT}
            toggleId={DAY_LOG_ITEM_FILTERS.SPOT}
            selectedId={selectedFilter}
            changeSelect={handleFilterSelectClick}
            aria-label={`${DAY_LOG_ITEM_FILTERS.SPOT} 필터`}
          />
        </ToggleGroup>
      </Flex>
      {selectedTripItemList.length > 0 ? (
        <TripItemList
          tripId={tripId}
          dayLogId={information.id}
          tripItems={selectedTripItemList}
          isEditable={isEditable}
        />
      ) : (
        <TripItemList.Empty tripId={tripId} isEditable={isEditable} openAddModal={openAddModal} />
      )}
    </Box>
  );
};

export default DayLogItem;
