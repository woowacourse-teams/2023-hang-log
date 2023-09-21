import { Tab, Tabs } from 'hang-log-design-system';

import DayLogItem from '@components/common/DayLogItem/DayLogItem';
import { containerStyling } from '@components/common/DayLogList/DayLogList.style';

import { useTrip } from '@hooks/trip/useTrip';

import { formatMonthDate } from '@utils/formatter';

import type { DayLogData } from '@type/dayLog';

interface DayLogListProps {
  tripId: number | string;
  selectedDayLog: DayLogData;
  isEditable?: boolean;
  isShared?: boolean;
  onTabChange: (selectedId: string | number) => void;
  openAddModal?: () => void;
}

const DayLogList = ({
  tripId,
  selectedDayLog,
  isEditable = true,
  isShared = false,
  onTabChange,
  openAddModal,
}: DayLogListProps) => {
  const { dates } = useTrip(tripId);

  return (
    <section css={containerStyling}>
      <Tabs>
        {dates.map((date, index) => {
          if (index === dates.length - 1) {
            return (
              <Tab
                key={date.id}
                text="기타"
                variant="outline"
                tabId={date.id}
                selectedId={selectedDayLog.id}
                changeSelect={onTabChange}
              />
            );
          }

          return (
            <Tab
              key={date.id}
              text={
                date.id === selectedDayLog.id
                  ? `Day ${index + 1} - ${formatMonthDate(date.date)} `
                  : `Day ${index + 1}`
              }
              variant="outline"
              tabId={date.id}
              selectedId={selectedDayLog.id}
              changeSelect={onTabChange}
            />
          );
        })}
      </Tabs>
      <DayLogItem
        tripId={tripId}
        isEditable={isEditable}
        isShared={isShared}
        openAddModal={openAddModal}
        {...selectedDayLog}
      />
    </section>
  );
};

export default DayLogList;
