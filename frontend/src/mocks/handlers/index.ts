import { cityHandlers } from '@mocks/handlers/city';
import { dayLogHandlers } from '@mocks/handlers/dayLog';
import { expenseHandlers } from '@mocks/handlers/expense';
import { tripItemHandlers } from '@mocks/handlers/tripItem';
import { tripsHandlers } from '@mocks/handlers/trips';

export const handlers = [
  ...cityHandlers,
  ...dayLogHandlers,
  ...expenseHandlers,
  ...tripsHandlers,
  ...tripItemHandlers,
];
