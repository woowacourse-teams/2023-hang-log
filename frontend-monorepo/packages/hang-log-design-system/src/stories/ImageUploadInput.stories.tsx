import type { Meta, StoryObj } from '@storybook/react';

import ImageUploadInput from '@components/ImageUploadInput/ImageUploadInput';

const meta = {
  title: 'ImageUploadInput',
  component: ImageUploadInput,
  argTypes: {
    label: {
      control: { type: 'text' },
    },
    supportingText: {
      control: { type: 'text' },
    },
    maxUploadCount: {
      control: { type: 'number' },
    },
  },
  args: {
    imageAltText: '이미지',
    imageUrls: null,
    maxUploadCount: 2,
  },
} satisfies Meta<typeof ImageUploadInput>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

export const WithUploadedImages: Story = {
  args: {
    imageAltText: '이미지',
    imageUrls: [
      'https://a.cdn-hotels.com/gdcs/production163/d1616/24e46678-07e1-4f27-93d3-9eb979c2ae5e.jpg',
      'https://cdn.sortiraparis.com/images/80/83517/529854-visuel-paris-marais.jpg',
    ],
    maxUploadCount: 3,
  },
};

export const MaximumImagesUploaded: Story = {
  args: {
    imageAltText: '이미지',
    imageUrls: [
      'https://a.cdn-hotels.com/gdcs/production163/d1616/24e46678-07e1-4f27-93d3-9eb979c2ae5e.jpg',
      'https://cdn.sortiraparis.com/images/80/83517/529854-visuel-paris-marais.jpg',
    ],
    maxUploadCount: 2,
  },
};
