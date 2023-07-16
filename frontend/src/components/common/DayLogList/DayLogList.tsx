import type { DayLogData } from '@type/dayLog';
import { Tab, Tabs, useSelect } from 'hang-log-design-system';

import { formatMonthDate } from '@utils/formatter';

import DayLogItem from '@components/common/DayLogItem/DayLogItem';
import { containerStyling } from '@components/common/DayLogList/DayLogList.style';

interface DayLogListProps {
  tripId: number;
  logs: DayLogData[];
}

const DayLogList = ({ tripId, logs }: DayLogListProps) => {
  const { selected, handleSelectClick } = useSelect(logs[0].id);
  const selectedDayLog = logs.find((log) => log.id === selected)!;

  return (
    <section css={containerStyling}>
      <Tabs>
        {logs.map((log, index) => (
          <Tab
            key={index}
            text={
              log.id === selected
                ? `Day ${log.ordinal} - ${formatMonthDate(log.date)} `
                : `Day ${log.ordinal}`
            }
            variant="outline"
            tabId={log.id}
            selectedId={selected}
            changeSelect={handleSelectClick}
          />
        ))}
      </Tabs>
      <DayLogItem tripId={tripId} {...selectedDayLog} />
    </section>
  );
};

export default DayLogList;
