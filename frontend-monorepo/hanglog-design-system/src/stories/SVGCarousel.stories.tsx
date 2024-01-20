import icon1 from '@assets/svg/add-icon.svg';
import icon2 from '@assets/svg/checked-icon.svg';
import icon3 from '@assets/svg/empty-star.svg';
import type { Meta, StoryObj } from '@storybook/react';

import SVGCarousel from '@components/SVGCarousel/SVGCarousel';

const meta = {
  title: 'SVGCarousel',
  component: SVGCarousel,
  argTypes: {
    width: { control: 'number' },
    height: { control: 'number' },
    showArrows: { control: 'boolean' },
    showDots: { control: 'boolean' },
    showNavigationOnHover: { control: 'boolean' },
    images: { control: false },
  },
  args: {
    width: 250,
    height: 167,
    showArrows: false,
    showDots: false,
    showNavigationOnHover: false,
  },
} satisfies Meta<typeof SVGCarousel>;

export default meta;
type Story = StoryObj<typeof SVGCarousel>;

const images = [icon1, icon2, icon3];

export const Default: Story = {
  render: ({ ...args }) => {
    return <SVGCarousel {...args} images={images} />;
  },
};

export const WithArrowButtons: Story = {
  render: ({ ...args }) => {
    return <SVGCarousel {...args} images={images} />;
  },
  args: {
    showArrows: true,
  },
};

export const WithDots: Story = {
  render: ({ ...args }) => {
    return <SVGCarousel {...args} images={images} />;
  },
  args: {
    showDots: true,
  },
};

export const ShowNavigationOnHover: Story = {
  render: ({ ...args }) => {
    return <SVGCarousel {...args} images={images} />;
  },
  args: {
    showArrows: true,
    showDots: true,
    showNavigationOnHover: true,
  },
};
