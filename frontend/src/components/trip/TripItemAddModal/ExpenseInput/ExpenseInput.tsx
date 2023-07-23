import { CURRENCY_ICON } from '@constants/trip';
import type { TripItemFormData } from '@type/tripItem';
import { Flex, Input, Label, Select, Theme } from 'hang-log-design-system';
import { memo } from 'react';

import { useTripItemExpenseInput } from '@hooks/trip/useTripItemExpenseInput';

import {
  categorySelectStyling,
  currencySelectStyling,
  leftContainerStyling,
} from '@components/trip/TripItemAddModal/ExpenseInput/ExpenseInput.style';

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
          onChange={handleCurrencyChange}
        >
          {Object.entries(CURRENCY_ICON).map(([key, value], index) => (
            <option key={key} value={key}>
              {value}
            </option>
          ))}
        </Select>
        <Input
          type="number"
          placeholder="0"
          value={initialExpenseValue?.amount}
          onChange={handleAmountChange}
        />
      </Flex>
    </Flex>
  );
};

export default memo(ExpenseInput);
