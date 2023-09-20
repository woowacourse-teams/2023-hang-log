import { useRecoilValue } from 'recoil';

import { Box, Heading } from 'hang-log-design-system';

import DonutChart from '@components/common/DonutChart/DonutChart';
import ExpenseCategoryInformation from '@components/expense/ExpenseCategoryInformation/ExpenseCategoryInformation';
import ExpenseInformation from '@components/expense/ExpenseInformation/ExpenseInformation';
import {
  containerStyling,
  totalAmountStyling,
} from '@components/expense/TotalExpenseSection/TotalExpenseSection.style';

import { useExpense } from '@hooks/expense/useExpense';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { formatNumberToMoney } from '@utils/formatter';

import { CURRENCY_ICON, DEFAULT_CURRENCY } from '@constants/trip';
import { EXPENSE_CATEGORY_CHART_SIZE, EXPENSE_CATEGORY_CHART_STROKE_WIDTH } from '@constants/ui';

interface TotalExpenseSectionProps {
  tripId: number;
  isShared: boolean;
}

const TotalExpenseSection = ({ tripId, isShared }: TotalExpenseSectionProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { expenseData, categoryChartData } = useExpense(tripId);

  return (
    <section css={containerStyling}>
      <ExpenseInformation tripId={expenseData.id} isShared={isShared} />
      <Heading size={isMobile ? 'xSmall' : 'small'} css={totalAmountStyling}>
        총 경비 :{' '}
        <span>
          {CURRENCY_ICON[DEFAULT_CURRENCY]}
          {formatNumberToMoney(Number(expenseData.totalAmount.toFixed(0)))}
        </span>
      </Heading>
      <Box
        css={{
          position: 'relative',
          width: EXPENSE_CATEGORY_CHART_SIZE,
        }}
      >
        <DonutChart
          segments={categoryChartData}
          size={EXPENSE_CATEGORY_CHART_SIZE}
          strokeWidth={EXPENSE_CATEGORY_CHART_STROKE_WIDTH}
        />
      </Box>
      <ExpenseCategoryInformation tripId={tripId} />
    </section>
  );
};

export default TotalExpenseSection;
