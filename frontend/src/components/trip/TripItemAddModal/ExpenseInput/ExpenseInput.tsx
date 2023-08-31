import { memo } from 'react';

import { Flex, Input, Label, Select, Theme } from 'hang-log-design-system';

import {
  categorySelectStyling,
  currencySelectStyling,
  leftContainerStyling,
} from '@components/trip/TripItemAddModal/ExpenseInput/ExpenseInput.style';

import { useTripItemExpenseInput } from '@hooks/trip/useTripItemExpenseInput';

import type { TripItemFormData } from '@type/tripItem';

import { CURRENCY_ICON } from '@constants/trip';
import { AMOUNT_MAX_LIMIT } from '@constants/ui';

interface ExpenseInputProps {
  initialExpenseValue: TripItemFormData['expense'];
  updateInputValue: <K extends keyof TripItemFormData>(key: K, value: TripItemFormData[K]) => void;
}

const ExpenseInput = ({ initialExpenseValue, updateInputValue }: ExpenseInputProps) => {
  const { expenseCategoryData, handleCategoryChange, handleCurrencyChange, handleAmountChange } =
    useTripItemExpenseInput(updateInputValue, initialExpenseValue);

  return (
    <Flex styles={{ direction: 'column', gap: Theme.spacer.spacing2 }}>
      <Label>비용</Label>
      <Flex styles={{ gap: Theme.spacer.spacing1 }} css={leftContainerStyling}>
        <Select
          css={categorySelectStyling}
          name="expense-category"
          value={initialExpenseValue?.categoryId}
          aria-label="비용 카테고리"
          onChange={handleCategoryChange}
        >
          {expenseCategoryData.map(({ id, name }) => (
            <option key={id} value={id}>
              {name}
            </option>
          ))}
        </Select>
        <Select
          css={currencySelectStyling}
          name="expense-currency"
          value={initialExpenseValue?.currency}
          aria-label="통화"
          onChange={handleCurrencyChange}
        >
          {Object.entries(CURRENCY_ICON).map(([key, value]) => (
            <option key={key} value={key}>
              {value}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;({key})
            </option>
          ))}
        </Select>
        <Input
          type="number"
          placeholder="0"
          aria-label="비용"
          min={0}
          max={AMOUNT_MAX_LIMIT}
          value={initialExpenseValue?.amount}
          onChange={handleAmountChange}
        />
      </Flex>
    </Flex>
  );
};

export default memo(ExpenseInput);
