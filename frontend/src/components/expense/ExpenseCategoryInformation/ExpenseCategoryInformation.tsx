import { CURRENCY_ICON, DEFAULT_CURRENCY } from '@constants/trip';
import { Flex, Text, Theme } from 'hang-log-design-system';

import { formatNumberToMoney } from '@utils/formatter';

import { useExpense } from '@hooks/expense/useExpense';

import {
  categoryNameStyling,
  containerStyling,
  getCategoryIconStyling,
  wrapperStyling,
} from '@components/expense/ExpenseCategoryInformation/ExpenseCategoryInformation.style';

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
            {formatNumberToMoney(amount)}
          </Text>
        </li>
      ))}
    </ul>
  );
};

export default ExpenseCategoryInformation;
