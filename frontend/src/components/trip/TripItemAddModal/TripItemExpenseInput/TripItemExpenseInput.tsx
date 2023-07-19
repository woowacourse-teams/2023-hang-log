import { CURRENCY_ICON } from '@constants/trip';
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
  const [_, setExpenseValue] = useState<TripItemFormType['expense']>(
    initialExpenseValue ?? {
      currency: 'KRW',
      categoryId: 100,
      amount: 0,
    }
  );

  const queryClient = useQueryClient();
  const expenseCategoryData = queryClient.getQueryData<ExpenseCategoryData[]>(['expenseCategory'])!;

  const handleCategoryChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setExpenseValue((prevExpenseValue) => {
      const newExpenseValue = { ...prevExpenseValue!, categoryId: Number(event.target.value) };

      if (prevExpenseValue?.amount) updateInputValue('expense', newExpenseValue);

      return newExpenseValue;
    });
  };

  const handleCurrencyChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setExpenseValue((prevExpenseValue) => {
      const newExpenseValue = { ...prevExpenseValue!, currency: event.target.value };

      if (prevExpenseValue?.amount) updateInputValue('expense', newExpenseValue);

      return newExpenseValue;
    });
  };

  const handleAmountChange = (event: ChangeEvent<HTMLInputElement>) => {
    setExpenseValue((prevExpenseValue) => {
      const newExpenseValue = {
        ...prevExpenseValue!,
        amount: Number(event.target.value),
      };
      updateInputValue('expense', newExpenseValue.amount === 0 ? null : newExpenseValue);

      return newExpenseValue;
    });
  };

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
