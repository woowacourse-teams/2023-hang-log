import type { CategoryExpenseType, ExpenseData, ExpenseItemData } from '@type/expense';

export const getItemsByCategory = (expenseData: ExpenseData): CategoryExpenseType => {
  const itemsByCategory: { [categoryId: number]: ExpenseItemData[] } = expenseData.dayLogs
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
};
