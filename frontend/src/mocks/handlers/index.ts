import { cityHandlers } from '@mocks/handlers/city';
import { dayLogHandlers } from '@mocks/handlers/dayLog';
import { tripHandlers } from '@mocks/handlers/trip';

export const handlers = [...tripHandlers, ...dayLogHandlers, ...cityHandlers];
