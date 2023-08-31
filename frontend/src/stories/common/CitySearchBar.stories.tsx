import type { Meta, StoryObj } from '@storybook/react';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';

import { cities } from '@mocks/data/city';

const meta = {
  title: 'common/CitySearchBar',
  component: CitySearchBar,
  args: {
    initialCities: cities.splice(0, 2),
    updateCityInfo: () => {},
  },
} satisfies Meta<typeof CitySearchBar>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
