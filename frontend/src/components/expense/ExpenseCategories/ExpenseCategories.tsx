import { Flex, Heading, Tab, Tabs, Text, Theme, useSelect } from 'hang-log-design-system';

import { totalAmountContainerStyling } from '@components/expense/ExpenseCategories/ExpenseCategories.style';
import ExpenseList from '@components/expense/ExpenseList/ExpenseList';

import { useExpense } from '@hooks/expense/useExpense';

import { formatDate, formatNumberToMoney } from '@utils/formatter';

import { CURRENCY_ICON, DEFAULT_CURRENCY } from '@constants/trip';

interface ExpenseCategoriesProps {
  tripId: number;
}

const ExpenseCategories = ({ tripId }: ExpenseCategoriesProps) => {
  const { categoryExpenseData } = useExpense(tripId);
  const { selected: selectedCategoryId, handleSelectClick: handleCategoryIdSelectClick } =
    useSelect(categoryExpenseData.categoryItems[0].category.id);
  const selectedCategory = categoryExpenseData.categoryItems.find(
    (categoryItem) => categoryItem.category.id === selectedCategoryId
  )!;

  return (
    <>
      <Tabs css={{ width: '100%' }}>
        {categoryExpenseData.categoryItems.map((categoryItem) => (
          <Tab
            key={categoryItem.category.id}
            text={categoryItem.category.name}
            variant="outline"
            tabId={categoryItem.category.id}
            selectedId={selectedCategoryId}
            changeSelect={handleCategoryIdSelectClick}
          />
        ))}
      </Tabs>
      {selectedCategory.items.length > 0 ? (
        <>
          <ExpenseList items={selectedCategory.items} />
          <Flex
            styles={{ direction: 'column', gap: Theme.spacer.spacing2 }}
            css={totalAmountContainerStyling}
          >
            <Heading size="xSmall">
              총 금액 :{' '}
              <span>
                {CURRENCY_ICON[DEFAULT_CURRENCY]}
                {formatNumberToMoney(selectedCategory.totalAmount)}
              </span>
            </Heading>
            <Text size="small">
              {formatDate(categoryExpenseData.exchangeRateDate)} 기준 환율이 적용되었습니다.
            </Text>
          </Flex>
        </>
      ) : (
        <ExpenseList.Empty tripId={tripId} />
      )}
    </>
  );
};

export default ExpenseCategories;
