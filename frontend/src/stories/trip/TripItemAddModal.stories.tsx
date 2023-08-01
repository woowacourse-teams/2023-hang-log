import { trip } from '@mocks/data/trip';
import type { Meta, StoryObj } from '@storybook/react';

import { useExpenseCategoryQuery } from '@hooks/api/useExpenseCategoryQuery';
import { useTripQuery } from '@hooks/api/useTripQuery';

import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

const meta = {
  title: 'trip/TripItemAddModal',
  component: TripItemAddModal,
  args: {
    tripId: 1,
    dayLogId: 1,
    onClose: () => {},
  },
  decorators: [
    (Story) => {
      useTripQuery(1);
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
      imageUrls: item.imageUrls,
    },
  },
};
