import { useCallback, useMemo } from 'react';

import { useQueryClient } from '@tanstack/react-query';

import type { Segment } from '@components/common/DonutChart/DonutChart';

import type { ExpenseData, ExpenseItemData } from '@type/expense';
import type { TripTypeData } from '@type/trip';

import { EXPENSE_CHART_COLORS } from '@constants/expense';

export const useExpense = (tripId: string, tripType: TripTypeData) => {
  const queryClient = useQueryClient();

  const expenseData = queryClient.getQueryData<ExpenseData>([tripType, 'expense', tripId])!;

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
  }, [expenseData.categories, expenseData.dayLogs, expenseData.exchangeRate.date]);

  const getCategoryChartData = useCallback(() => {
    return expenseData.categories.reduce<Segment[]>((acc, curr) => {
      if (curr.percentage !== 0) {
        const data = {
          id: curr.category.id,
          percentage: curr.percentage,
          color: EXPENSE_CHART_COLORS[curr.category.name],
        };

        acc.push(data);
      }

      return acc;
    }, []);
  }, [expenseData.categories]);

  const categoryExpenseData = useMemo(() => getItemsByCategory(), [getItemsByCategory]);

  const categoryChartData = useMemo(() => getCategoryChartData(), [getCategoryChartData]);

  return { expenseData, dates, categoryExpenseData, categoryChartData };
};
