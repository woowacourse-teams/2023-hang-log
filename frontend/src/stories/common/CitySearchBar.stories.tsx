import { cities } from '@mocks/data/city';
import type { Meta, StoryObj } from '@storybook/react';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';

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
