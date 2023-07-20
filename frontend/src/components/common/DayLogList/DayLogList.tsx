import type { DayLogData } from '@type/dayLog';
import { Tab, Tabs, useSelect } from 'hang-log-design-system';

import { formatMonthDate } from '@utils/formatter';

import DayLogItem from '@components/common/DayLogItem/DayLogItem';
import { containerStyling } from '@components/common/DayLogList/DayLogList.style';

interface DayLogListProps {
  tripId: number;
  selectedDayLog: DayLogData;
  dates: {
    id: number;
    date: string;
  }[];
  onTabChange: (selectedId: string | number) => void;
  openAddModal: () => void;
}

const DayLogList = ({
  tripId,
  selectedDayLog,
  dates,
  onTabChange,
  openAddModal,
}: DayLogListProps) => {
  return (
    <section css={containerStyling}>
      <Tabs>
        {dates.map((date, index) => (
          <Tab
            key={index}
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
        ))}
      </Tabs>
      <DayLogItem tripId={tripId} openAddModal={openAddModal} {...selectedDayLog} />
    </section>
  );
};

export default DayLogList;
