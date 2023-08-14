import { Flex, Heading, Tab, Tabs, Text, Theme, useSelect } from 'hang-log-design-system';

import { totalAmountContainerStyling } from '@components/expense/ExpenseDates/ExpenseDates.style';
import ExpenseList from '@components/expense/ExpenseList/ExpenseList';

import { useExpense } from '@hooks/expense/useExpense';

import { formatDate, formatMonthDate, formatNumberToMoney } from '@utils/formatter';

import { CURRENCY_ICON, DEFAULT_CURRENCY } from '@constants/trip';

interface ExpenseDatesProps {
  tripId: number;
}

const ExpenseDates = ({ tripId }: ExpenseDatesProps) => {
  const { expenseData, dates } = useExpense(tripId);

  const { selected: selectedDayLogId, handleSelectClick: handleDayLogIdSelectClick } = useSelect(
    expenseData.dayLogs[0].id
  );
  const selectedDayLog = expenseData.dayLogs.find((log) => log.id === selectedDayLogId)!;

  return (
    <>
      <Tabs css={{ width: '100%' }}>
        {dates.map((date, index) => {
          if (index === dates.length - 1) {
            return (
              <Tab
                key={date.id}
                text="기타"
                variant="outline"
                tabId={date.id}
                selectedId={selectedDayLog.id}
                changeSelect={handleDayLogIdSelectClick}
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
              changeSelect={handleDayLogIdSelectClick}
            />
          );
        })}
      </Tabs>
      {selectedDayLog.items.length > 0 ? (
        <>
          <ExpenseList items={selectedDayLog.items} />
          <Flex
            styles={{ direction: 'column', gap: Theme.spacer.spacing2 }}
            css={totalAmountContainerStyling}
          >
            <Heading size="xSmall">
              총 금액 :{' '}
              <span>
                {CURRENCY_ICON[DEFAULT_CURRENCY]}
                {formatNumberToMoney(selectedDayLog.totalAmount)}
              </span>
            </Heading>
            <Text size="small">
              {formatDate(expenseData.exchangeRate.date)} 기준 환율이 적용되었습니다.
            </Text>
          </Flex>
        </>
      ) : (
        <ExpenseList.Empty tripId={tripId} />
      )}
    </>
  );
};

export default ExpenseDates;
