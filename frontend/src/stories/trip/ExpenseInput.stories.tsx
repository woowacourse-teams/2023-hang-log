import type { Meta, StoryObj } from '@storybook/react';

import { useState } from 'react';

import ExpenseInput from '@components/trip/TripItemAddModal/ExpenseInput/ExpenseInput';

import { useExpenseCategoryQuery } from '@hooks/api/useExpenseCategoryQuery';

import type { TripItemFormData } from '@type/tripItem';

const meta = {
  title: 'trip/TripItemAddModal/ExpenseInput',
  component: ExpenseInput,
  args: {
    initialExpenseValue: null,
  },
  decorators: [
    (Story) => {
      useExpenseCategoryQuery();

      return <Story />;
    },
  ],
} satisfies Meta<typeof ExpenseInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => {
    const [value, setValue] = useState<TripItemFormData['expense']>(null);

    const updateInputValue = <K extends keyof TripItemFormData>(
      id: K,
      value: TripItemFormData[K]
    ) => {
      setValue(value as TripItemFormData['expense']);
    };

    return <ExpenseInput initialExpenseValue={value} updateInputValue={updateInputValue} />;
  },
};
