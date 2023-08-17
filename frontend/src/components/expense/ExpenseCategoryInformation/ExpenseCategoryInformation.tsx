import { Flex, Text, Theme } from 'hang-log-design-system';

import {
  categoryNameStyling,
  containerStyling,
  getCategoryIconStyling,
  wrapperStyling,
} from '@components/expense/ExpenseCategoryInformation/ExpenseCategoryInformation.style';

import { useExpense } from '@hooks/expense/useExpense';

import { formatNumberToMoney } from '@utils/formatter';

import { CURRENCY_ICON, DEFAULT_CURRENCY } from '@constants/trip';

interface ExpenseCategoryInformationProps {
  tripId: number;
}

const ExpenseCategoryInformation = ({ tripId }: ExpenseCategoryInformationProps) => {
  const { expenseData } = useExpense(tripId);

  return (
    <ul css={containerStyling}>
      {expenseData.categories.map(({ category, amount }) => (
        <li key={category.id} css={wrapperStyling}>
          <Flex styles={{ align: 'center', gap: Theme.spacer.spacing2 }}>
            <div css={getCategoryIconStyling(category.name)} />
            <Text css={categoryNameStyling}>{category.name}</Text>
          </Flex>
          <Text>
            {CURRENCY_ICON[DEFAULT_CURRENCY]}
            {formatNumberToMoney(Number(amount.toFixed(1)))}
          </Text>
        </li>
      ))}
    </ul>
  );
};

export default ExpenseCategoryInformation;
