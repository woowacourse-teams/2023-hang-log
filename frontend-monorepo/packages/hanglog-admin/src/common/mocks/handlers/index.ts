import { cityHandlers } from '@mocks/handlers/city';

import { adminMemberHandlers } from './adminMember';
import { categoryHandlers } from './category';
import { currencyHandlers } from './currency';

export const handlers = [
  ...cityHandlers,
  ...categoryHandlers,
  ...currencyHandlers,
  ...adminMemberHandlers,
];
