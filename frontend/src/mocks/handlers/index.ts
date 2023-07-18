import { cityHandlers } from '@mocks/handlers/city';
import { dayLogHandlers } from '@mocks/handlers/dayLog';
import { tripHandlers } from '@mocks/handlers/trip';
import { tripsHandler } from '@mocks/handlers/trips';

export const handlers = [...cityHandlers, ...dayLogHandlers, ...tripHandlers, ...tripsHandler];
