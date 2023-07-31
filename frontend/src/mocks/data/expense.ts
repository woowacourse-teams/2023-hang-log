import type { ExpenseCategoryData, ExpenseData } from '@type/expense';

export const expense: ExpenseData = {
  id: 1,
  title: '런던 여행',
  startDate: '2023-07-01',
  endDate: '2023-07-04',
  cities: [
    {
      id: 1,
      name: '런던',
    },
    {
      id: 2,
      name: '애든버러',
    },
    {
      id: 3,
      name: '파리',
    },
  ],
  totalAmount: 210000,
  categories: [
    {
      category: {
        id: 100,
        name: '음식',
      },
      amount: 500000,
      percentage: 2,
    },
    {
      category: {
        id: 200,
        name: '문화',
      },
      amount: 300000,
      percentage: 2,
    },
    {
      category: {
        id: 300,
        name: '쇼핑',
      },
      amount: 300000,
      percentage: 2,
    },
    {
      category: {
        id: 400,
        name: '숙박',
      },
      amount: 500000,
      percentage: 2,
    },
    {
      category: {
        id: 500,
        name: '교통',
      },
      amount: 500000,
      percentage: 2,
    },
  ],
  exchangeRate: {
    date: '2023-07-31',
    currencyRates: [
      {
        currency: 'EUR',
        rate: 1300,
      },
      {
        currency: 'GBP',
        rate: 1641.21,
      },
    ],
  },
  dayLogs: [
    {
      id: 1,
      ordinal: 1,
      date: '2023-07-01',
      totalAmount: 400000,
      items: [
        {
          id: 1,
          title: '택시',
          ordinal: 1,
          expense: {
            id: 1,
            currency: 'GBP',
            amount: 24,
            category: {
              id: 500,
              name: '교통',
            },
          },
        },
        {
          id: 2,
          title: '조 말론 런던',
          ordinal: 2,
          expense: {
            id: 2,
            currency: 'GBP',
            amount: 350,
            category: {
              id: 300,
              name: '쇼핑',
            },
          },
        },
      ],
    },
    {
      id: 2,
      ordinal: 2,
      date: '2023-07-02',
      totalAmount: 300000,
      items: [
        {
          id: 1,
          title: '택시',
          ordinal: 1,
          expense: {
            id: 1,
            currency: 'GBP',
            amount: 24,
            category: {
              id: 500,
              name: '교통',
            },
          },
        },
        {
          id: 2,
          title: 'Barnes & Noble',
          ordinal: 2,
          expense: {
            id: 2,
            currency: 'GBP',
            amount: 150,
            category: {
              id: 300,
              name: '쇼핑',
            },
          },
        },
        {
          id: 3,
          title: '기차',
          ordinal: 3,
          expense: {
            id: 2,
            currency: 'GBP',
            amount: 150,
            category: {
              id: 500,
              name: '교통',
            },
          },
        },
      ],
    },
    {
      id: 3,
      ordinal: 3,
      date: '2023-07-03',
      totalAmount: 20000,
      items: [
        {
          id: 1,
          title: '택시',
          ordinal: 1,
          expense: {
            id: 1,
            currency: 'EUR',
            amount: 32,
            category: {
              id: 500,
              name: '교통',
            },
          },
        },
      ],
    },
    {
      id: 4,
      ordinal: 4,
      date: '2023-07-04',
      totalAmount: 20000,
      items: [
        {
          id: 1,
          title: '공항 택시',
          ordinal: 1,
          expense: {
            id: 1,
            currency: 'KRW',
            amount: 250000,
            category: {
              id: 500,
              name: '교통',
            },
          },
        },
      ],
    },
  ],
};

export const expenseCategories: ExpenseCategoryData[] = [
  {
    id: 100,
    name: '음식',
  },
  {
    id: 200,
    name: '문화',
  },
  {
    id: 300,
    name: '쇼핑',
  },
  {
    id: 400,
    name: '숙박',
  },
  {
    id: 500,
    name: '교통',
  },
  {
    id: 600,
    name: '기타',
  },
];
