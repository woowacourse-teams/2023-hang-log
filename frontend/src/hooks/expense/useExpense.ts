import { useCallback, useMemo } from 'react';

import { useQueryClient } from '@tanstack/react-query';

import type { ExpenseData, ExpenseItemData } from '@type/expense';

export const useExpense = (tripId: number) => {
  const queryClient = useQueryClient();

  const expenseData = queryClient.getQueryData<ExpenseData>(['expense', tripId])!;

  const dates = expenseData.dayLogs.map((data) => ({
    id: data.id,
    date: data.date,
  }));

  const getItemsByCategory = useCallback(() => {
    const itemsByCategory = expenseData.dayLogs
      .flatMap((dayLog) => dayLog.items)
      .reduce<{ [categoryId: number]: ExpenseItemData[] }>((acc, item) => {
        const categoryId = item.expense.category.id;
        acc[categoryId] = acc[categoryId] || [];
        acc[categoryId].push(item);

        return acc;
      }, {});

    return {
      exchangeRateDate: expenseData.exchangeRate.date,
      categoryItems: expenseData.categories.map((categoryItem) => {
        const categoryId = categoryItem.category.id;

        return {
          category: categoryItem.category,
          totalAmount: categoryItem.amount || 0,
          items: itemsByCategory[categoryId] || [],
        };
      }),
    };
  }, [expenseData]);

  const categoryExpenseData = useMemo(() => getItemsByCategory(), [getItemsByCategory]);

  return { expenseData, dates, categoryExpenseData };
};
