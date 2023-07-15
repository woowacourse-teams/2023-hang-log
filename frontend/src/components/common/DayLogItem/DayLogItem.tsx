import { DAY_LOG_ITEM_FILTERS } from '@constants/trip';
import type { DayLogItemData } from '@type/dayLog';
import { Box, Flex, Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';
import { useEffect, useState } from 'react';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TitleInput from '@components/common/DayLogItem/TitleInput/TitleInput';
import TripItemList from '@components/common/TripItemList/TripItemList';

type DayLogItemProps = DayLogItemData;

const DayLogItem = ({ ...information }: DayLogItemProps) => {
  const { selected, handleSelectClick } = useSelect(DAY_LOG_ITEM_FILTERS.ALL);
  const [tripItemList, setTripItemList] = useState(information.items);

  useEffect(() => {
    /** ordinal 변경되었을 때 목록 및 선택된 토클 초기화 */
    setTripItemList(information.items);
    handleSelectClick(DAY_LOG_ITEM_FILTERS.ALL);
  }, [information.items]);

  const handleToggleChange = (selectedId: string | number) => {
    handleSelectClick(selectedId);

    if (selectedId === DAY_LOG_ITEM_FILTERS.ALL) {
      setTripItemList(information.items);
    }

    if (selectedId === DAY_LOG_ITEM_FILTERS.SPOT) {
      const newList = information.items.filter((item) => item.itemType === true);
      setTripItemList(newList);
    }
  };

  return (
    <Box css={containerStyling}>
      <Flex css={headerStyling} styles={{ justify: 'space-between' }}>
        {/* 수정 모드일 때만 보인다 */}
        <TitleInput initialTitle={information.title} />
        <ToggleGroup>
          <Toggle
            text={DAY_LOG_ITEM_FILTERS.ALL}
            toggleId={DAY_LOG_ITEM_FILTERS.ALL}
            selectedId={selected}
            changeSelect={handleToggleChange}
          />
          <Toggle
            text={DAY_LOG_ITEM_FILTERS.SPOT}
            toggleId={DAY_LOG_ITEM_FILTERS.SPOT}
            selectedId={selected}
            changeSelect={handleToggleChange}
          />
        </ToggleGroup>
      </Flex>
      {tripItemList.length > 0 ? <TripItemList items={tripItemList} /> : <TripItemList.Empty />}
    </Box>
  );
};

export default DayLogItem;
