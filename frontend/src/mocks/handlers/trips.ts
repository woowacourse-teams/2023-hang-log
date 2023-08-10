import { END_POINTS } from '@constants/api';
import { trip } from '@mocks/data/trip';
import { trips } from '@mocks/data/trips';
import type { TripFormData } from '@type/trip';
import { rest } from 'msw';

export const tripsHandlers = [
  rest.get(`${END_POINTS.TRIPS}`, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(trips));
  }),

  rest.get(`${END_POINTS.TRIPS}/none`, (_, res, ctx) => {
    return res(ctx.status(200), ctx.json([]));
  }),

  rest.post(`${END_POINTS.TRIPS}`, (_, res, ctx) => {
    return res(ctx.status(201), ctx.set('Location', '/trips/1'));
  }),

  rest.get(`${END_POINTS.TRIPS}/:tripId`, (_, res, ctx) => {
    return res(ctx.delay(2000), ctx.status(200), ctx.json(trip));
  }),

  rest.put(`${END_POINTS.TRIPS}/:tripId`, async (req, res, ctx) => {
    const response = await req.json<TripFormData>();

    trip.title = response.title;
    trip.startDate = response.startDate;
    trip.endDate = response.endDate;
    trip.description = response.description;
    trip.imageUrl = response.imageUrl;

    return res(ctx.status(204));
  }),

  rest.delete(`${END_POINTS.TRIPS}/:tripId`, (req, res, ctx) => {
    trips.splice(0, 1);

    return res(ctx.status(204));
  }),

  rest.patch(`${END_POINTS.TRIPS}/:tripId/share`, async (req, res, ctx) => {
    const { sharedStatus } = await req.json();
    const sharedUrl = sharedStatus ? 'https://hanglog.com/sharedTrips/xxxxxxx' : null;

    return res(ctx.status(200), ctx.json({ sharedUrl }));
  }),

  rest.get(`${END_POINTS.TRIPS}/:tripId/share`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json({ sharedCode: '123456' }));
  }),
];
