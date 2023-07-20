import { useTripItemExpenseInput } from '@/hooks/useTripItemExpenseInput';
import { CURRENCY_ICON, DEFAULT_CURRENCY } from '@constants/trip';
import { useQueryClient } from '@tanstack/react-query';
import type { ExpenseCategoryData } from '@type/expense';
import type { TripItemFormType } from '@type/tripItem';
import { Flex, Input, Label, Select, Theme } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo, useState } from 'react';

import {
  categorySelectStyling,
  currencySelectStyling,
  leftContainerStyling,
} from '@components/trip/TripItemAddModal/TripItemExpenseInput/TripItemExpenseInput.style';

interface TripItemExpenseInputProps {
  initialExpenseValue?: TripItemFormType['expense'];
  updateInputValue: <K extends keyof TripItemFormType>(key: K, value: TripItemFormType[K]) => void;
}

const TripItemExpenseInput = ({
  initialExpenseValue,
  updateInputValue,
}: TripItemExpenseInputProps) => {
  const { expenseCategoryData, handleCategoryChange, handleCurrencyChange, handleAmountChange } =
    useTripItemExpenseInput(updateInputValue, initialExpenseValue);

  return (
    <Flex styles={{ direction: 'column', gap: Theme.spacer.spacing2 }}>
      <Label>비용</Label>
      <Flex styles={{ gap: Theme.spacer.spacing1 }} css={leftContainerStyling}>
        <Select css={categorySelectStyling} name="expense-category" onChange={handleCategoryChange}>
          {expenseCategoryData.map(({ id, name }) => (
            <option key={id} value={id}>
              {name}
            </option>
          ))}
        </Select>
        <Select css={currencySelectStyling} name="expense-currency" onChange={handleCurrencyChange}>
          {Object.entries(CURRENCY_ICON).map(([key, value], index) => (
            <option key={key} value={key}>
              {value}
            </option>
          ))}
        </Select>
        <Input type="number" placeholder="0" onChange={handleAmountChange} />
      </Flex>
    </Flex>
  );
};

export default memo(TripItemExpenseInput);
