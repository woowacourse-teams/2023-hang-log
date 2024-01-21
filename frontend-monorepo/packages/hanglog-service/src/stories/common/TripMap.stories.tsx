import type { Meta, StoryObj } from '@storybook/react';

import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripMap from '@components/common/TripMap/TripMap';

import { trip } from '@mocks/data/trip';

const places = trip.dayLogs[0].items
  .filter((item) => item.itemType)
  .map((item) => ({
    id: item.id,
    name: item.title,
    coordinate: { lat: item.place!.latitude, lng: item.place!.longitude },
  }));

const meta = {
  title: 'common/TripMap',
  component: TripMap,
  argTypes: {
    places: { control: false },
  },
  args: {
    centerLat: 40.73061,
    centerLng: -73.935242,
    places: [],
  },
  decorators: [
    (Story) => (
      <GoogleMapWrapper>
        <Story />
      </GoogleMapWrapper>
    ),
  ],
} satisfies Meta<typeof TripMap>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

export const WithMarkers: Story = {
  argTypes: {
    centerLat: { control: false },
    centerLng: { control: false },
  },
  args: {
    places,
  },
};
