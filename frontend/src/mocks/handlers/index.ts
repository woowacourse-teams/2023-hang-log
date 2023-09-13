import { cityHandlers } from '@mocks/handlers/city';
import { dayLogHandlers } from '@mocks/handlers/dayLog';
import { expenseHandlers } from '@mocks/handlers/expense';
import { imageHandlers } from '@mocks/handlers/image';
import { memberHandlers } from '@mocks/handlers/member';
import { myTripsHandlers } from '@mocks/handlers/myTrips';
import { tripItemHandlers } from '@mocks/handlers/tripItem';

import { recommendedTripsHandlers } from './recommendedTrips';

export const handlers = [
  ...cityHandlers,
  ...dayLogHandlers,
  ...expenseHandlers,
  ...myTripsHandlers,
  ...tripItemHandlers,
  ...imageHandlers,
  ...memberHandlers,
  ...recommendedTripsHandlers,
];
