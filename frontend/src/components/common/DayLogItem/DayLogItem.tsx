import { useEffect } from 'react';

import { Box, Flex, Heading, Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TitleInput from '@components/common/DayLogItem/TitleInput/TitleInput';
import TripItemList from '@components/common/TripItemList/TripItemList';

import type { DayLogData } from '@type/dayLog';
import type { TripTypeData } from '@type/trip';

import { DAY_LOG_ITEM_FILTERS, TRIP_TYPE } from '@constants/trip';

interface DayLogItemProps extends DayLogData {
  tripId: string;
  tripType: TripTypeData;
  isEditable?: boolean;
  openAddModal?: () => void;
}

const DayLogItem = ({
  tripId,
  tripType,
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
        {!isEditable && (
          <ToggleGroup>
            {[DAY_LOG_ITEM_FILTERS.ALL, DAY_LOG_ITEM_FILTERS.SPOT].map((filter) => (
              <Toggle
                key={filter}
                text={filter}
                toggleId={filter}
                selectedId={selectedFilter}
                changeSelect={handleFilterSelectClick}
                aria-label={`${filter} 필터`}
              />
            ))}
          </ToggleGroup>
        )}
      </Flex>
      {selectedTripItemList.length > 0 ? (
        <TripItemList
          tripId={tripId}
          dayLogId={information.id}
          tripItems={selectedTripItemList}
          isEditable={isEditable}
        />
      ) : (
        <TripItemList.Empty
          tripId={tripId}
          isEditable={isEditable}
          isShared={tripType === TRIP_TYPE.SHARED || tripType === TRIP_TYPE.PUBLISHED}
          openAddModal={openAddModal}
        />
      )}
    </Box>
  );
};

export default DayLogItem;
