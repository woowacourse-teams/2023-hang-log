import { cityHandlers } from '@mocks/handlers/city';
import { communityTripsHandlers } from '@mocks/handlers/communityTrips';
import { dayLogHandlers } from '@mocks/handlers/dayLog';
import { expenseHandlers } from '@mocks/handlers/expense';
import { imageHandlers } from '@mocks/handlers/image';
import { likeHandlers } from '@mocks/handlers/like';
import { memberHandlers } from '@mocks/handlers/member';
import { myTripsHandlers } from '@mocks/handlers/myTrips';
import { recommendedTripsHandlers } from '@mocks/handlers/recommendedTrips';
import { tripItemHandlers } from '@mocks/handlers/tripItem';

export const handlers = [
  ...likeHandlers,
  ...cityHandlers,
  ...dayLogHandlers,
  ...expenseHandlers,
  ...myTripsHandlers,
  ...tripItemHandlers,
  ...imageHandlers,
  ...memberHandlers,
  ...recommendedTripsHandlers,
  ...communityTripsHandlers,
];
