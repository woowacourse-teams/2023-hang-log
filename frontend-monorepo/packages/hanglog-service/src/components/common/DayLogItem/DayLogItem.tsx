import { useEffect, useState } from 'react';

import { Box, Flex, Heading, NewToggle as Toggle } from 'hang-log-design-system';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TitleInput from '@components/common/DayLogItem/TitleInput/TitleInput';
import TripItemList from '@components/common/TripItemList/TripItemList';

import type { DayLogData } from '@type/dayLog';
import type { TripTypeData } from '@type/trip';
import { TripItemData } from '@type/tripItem';

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
  const [dayLogLists, setDayLogLists] = useState<TripItemData[]>([]);

  const handleChangeSelect = (key: number | string) => {
    const resultList =
      key === DAY_LOG_ITEM_FILTERS.SPOT
        ? information.items.filter((item) => item.itemType === true)
        : information.items;

    setDayLogLists(resultList);
  };

  useEffect(() => {
    setDayLogLists(information.items);
  }, [information.id, information.items.length]);

  return (
    <Box tag="section" css={containerStyling}>
      <Flex css={headerStyling} styles={{ justify: 'space-between' }}>
        {isEditable ? (
          <TitleInput tripId={tripId} dayLogId={information.id} initialTitle={information.title} />
        ) : (
          <Heading size="xSmall">{information.title}</Heading>
        )}
        {!isEditable && (
          <Toggle initialSelect={DAY_LOG_ITEM_FILTERS.ALL} additinalFunc={handleChangeSelect}>
            <Flex>
              {[DAY_LOG_ITEM_FILTERS.ALL, DAY_LOG_ITEM_FILTERS.SPOT].map((filter) => (
                <Toggle.List
                  key={filter}
                  text={filter}
                  toggleKey={filter}
                  aria-label={`${filter} 필터`}
                />
              ))}
            </Flex>
          </Toggle>
        )}
      </Flex>
      {dayLogLists.length > 0 ? (
        <TripItemList
          tripId={tripId}
          dayLogId={information.id}
          tripItems={dayLogLists}
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
