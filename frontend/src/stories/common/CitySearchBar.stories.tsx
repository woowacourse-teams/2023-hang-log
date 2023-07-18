import type { Meta, StoryObj } from '@storybook/react';

import CitySearchBar from '@components/common/CitySearchBar/CitySearchBar';

const meta = {
  title: 'common/CitySearchBar',
  component: CitySearchBar,
  args: {
    initialCityTags: [
      { id: 16, name: '오사카, 일본' },
      { id: 13, name: '부산, 한국' },
      { id: 20, name: '제주도, 한국' },
      { id: 21, name: '오클로호마, 미국' },
    ],
  },
} satisfies Meta<typeof CitySearchBar>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
