import { dayLogHandlers } from '@mocks/handlers/dayLog';
import { tripHandlers } from '@mocks/handlers/trip';
import { tripsHandler } from '@mocks/handlers/trips';

export const handlers = [...dayLogHandlers, ...tripHandlers, ...tripsHandler];
