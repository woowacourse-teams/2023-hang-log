import type { Meta, StoryObj } from '@storybook/react';

import ImageCarousel from '@components/ImageCarousel/ImageCarousel';

const meta = {
  title: 'ImageCarousel',
  component: ImageCarousel,
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
    isDraggable: true,
  },
} satisfies Meta<typeof ImageCarousel>;

export default meta;
type Story = StoryObj<typeof ImageCarousel>;

const images = [
  'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/La_Tour_Eiffel_vue_de_la_Tour_Saint-Jacques%2C_Paris_ao%C3%BBt_2014_%282%29.jpg/1200px-La_Tour_Eiffel_vue_de_la_Tour_Saint-Jacques%2C_Paris_ao%C3%BBt_2014_%282%29.jpg',
  'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/02/57/44/0c/filename-img-1097-jpg.jpg?w=700&h=-1&s=1',
  'https://imageio.forbes.com/specials-images/imageserve/646b6b45d9b20ac15900fd8a/0x0.jpg?format=jpg&width=1200',
];

export const Default: Story = {
  render: ({ ...args }) => {
    return <ImageCarousel {...args} images={images} />;
  },
};

export const WithArrowButtons: Story = {
  render: ({ ...args }) => {
    return <ImageCarousel {...args} images={images} />;
  },
  args: {
    showArrows: true,
  },
};

export const WithDots: Story = {
  render: ({ ...args }) => {
    return <ImageCarousel {...args} images={images} />;
  },
  args: {
    showDots: true,
  },
};

export const ShowNavigationOnHover: Story = {
  render: ({ ...args }) => {
    return <ImageCarousel {...args} images={images} />;
  },
  args: {
    showArrows: true,
    showDots: true,
    showNavigationOnHover: true,
  },
};
