import type { Meta, StoryObj } from '@storybook/react';

import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

import { useExpenseCategoryQuery } from '@hooks/api/useExpenseCategoryQuery';
import { useTripQuery } from '@hooks/api/useTripQuery';

import { trip } from '@mocks/data/trip';

const meta = {
  title: 'trip/TripItemAddModal',
  component: TripItemAddModal,
  args: {
    tripId: '1',
    dayLogId: 1,
    onClose: () => {},
  },
  decorators: [
    (Story) => {
      useTripQuery('PERSONAL', '1');
      useExpenseCategoryQuery();

      return <Story />;
    },
  ],
} satisfies Meta<typeof TripItemAddModal>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

const item = trip.dayLogs[0].items[0];

export const WithInitialData: Story = {
  args: {
    itemId: item.id,
    initialData: {
      itemType: item.itemType,
      dayLogId: 1,
      title: item.title,
      isPlaceUpdated: false,
      place: null,
      rating: item.rating,
      expense: {
        currency: item.expense!.currency,
        amount: item.expense!.amount,
        categoryId: item.expense!.id,
      },
      memo: item.memo,
      imageNames: item.imageNames,
    },
  },
};
