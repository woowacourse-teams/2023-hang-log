import { Flex, Heading, Text, Theme } from 'hang-log-design-system';

import { amountStyling, containerStyling } from '@components/expense/ExpenseItem/ExpenseItem.style';

import { formatNumberToMoney } from '@utils/formatter';

import type { ExpenseItemData } from '@type/expense';

import { CURRENCY_ICON } from '@constants/trip';

type ExpenseItemProps = ExpenseItemData;

const ExpenseItem = ({ ...information }: ExpenseItemProps) => {
  return (
    <Flex tag="li" styles={{ justify: 'space-between', align: 'center' }} css={containerStyling}>
      <Flex styles={{ direction: 'column', gap: Theme.spacer.spacing1 }} css={{ maxWidth: '100%' }}>
        <Heading size="xSmall" css={{ wordBreak: 'break-all' }}>
          {information.title}
        </Heading>
        <Text>{information.expense.category.name}</Text>
      </Flex>
      <Heading size="xSmall" css={amountStyling}>
        {CURRENCY_ICON[information.expense.currency]}
        {formatNumberToMoney(information.expense.amount)}
      </Heading>
    </Flex>
  );
};

export default ExpenseItem;
