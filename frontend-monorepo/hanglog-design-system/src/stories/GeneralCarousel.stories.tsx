/* eslint-disable react/no-array-index-key */

/* eslint-disable jsx-a11y/img-redundant-alt */
import type { Meta, StoryObj } from '@storybook/react';

import Carousel from '@components/GeneralCarousel/Carousel';

const meta = {
  title: 'Carousel',
  component: Carousel,
  argTypes: {
    width: { control: 'number' },
    height: { control: 'number' },
  },
  args: {
    width: 300,
    height: 200,
    length: 3,
  },
} satisfies Meta<typeof Carousel>;

const images = [
  'https://i.pinimg.com/236x/18/0e/c6/180ec6aaf4b5aab89d91f36752219569.jpg',
  'https://img.freepik.com/free-photo/many-ripe-juicy-red-apples-covered-with-water-drops-closeup-selective-focus-ripe-fruits-as-a-background_166373-2611.jpg?size=626&ext=jpg&ga=GA1.1.1546980028.1703808000&semt=sph',
  'https://img.freepik.com/premium-photo/a-red-apple-with-a-white-background-and-a-white-background_933356-5.jpg',
];

export default meta;
type Story = StoryObj<typeof Carousel>;

export const Default: Story = {
  render: ({ ...args }) => {
    return (
      <Carousel {...args}>
        {images.map((url, index) => (
          <Carousel.Item index={index} key={index}>
            <img src={url} alt="image" />
          </Carousel.Item>
        ))}
      </Carousel>
    );
  },
};
