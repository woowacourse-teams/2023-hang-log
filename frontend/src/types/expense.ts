export interface ExpenseCategoryData {
  id: number;
  name: string;
}

interface ExpenseItemData {
  id: number;
  title: string;
  ordinal: number;
  expense: {
    id: number;
    currency: string;
    amount: number;
    category: ExpenseCategoryData;
  };
}

export interface ExpenseData {
  id: number;
  title: string;
  startDate: string;
  endDate: string;
  cities: {
    id: number;
    name: string;
  }[];
  totalAmount: number;
  categories: {
    category: ExpenseCategoryData;
    amount: number;
    percentage: number;
  }[];
  exchangeRate: {
    date: string;
    currencyRates: {
      currency: string;
      rate: number;
    }[];
  };
  dayLogs: {
    id: number;
    ordinal: number;
    date: string;
    totalAmount: number;
    items: ExpenseItemData[];
  }[];
}
