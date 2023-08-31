import { Box, Flex, Heading } from 'hang-log-design-system';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TitleInput from '@components/common/DayLogItem/TitleInput/TitleInput';
import TripItemList from '@components/common/TripItemList/TripItemList';

import type { DayLogData } from '@type/dayLog';

interface DayLogItemProps extends DayLogData {
  tripId: number;
  isEditable?: boolean;
  isShared?: boolean;
  openAddModal?: () => void;
}

const DayLogItem = ({
  tripId,
  isEditable = true,
  isShared = false,
  openAddModal,
  ...information
}: DayLogItemProps) => {
  return (
    <Box tag="section" css={containerStyling}>
      <Flex css={headerStyling} styles={{ justify: 'space-between' }}>
        {isEditable ? (
          <TitleInput tripId={tripId} dayLogId={information.id} initialTitle={information.title} />
        ) : (
          <Heading size="xSmall">{information.title}</Heading>
        )}
      </Flex>
      {information.items.length > 0 ? (
        <TripItemList
          tripId={tripId}
          dayLogId={information.id}
          tripItems={information.items}
          isEditable={isEditable}
        />
      ) : (
        <TripItemList.Empty
          tripId={tripId}
          isEditable={isEditable}
          isShared={isShared}
          openAddModal={openAddModal}
        />
      )}
    </Box>
  );
};

export default DayLogItem;
