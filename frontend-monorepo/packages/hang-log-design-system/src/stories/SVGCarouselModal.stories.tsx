import icon1 from '@assets/svg/add-icon.svg';
import icon2 from '@assets/svg/checked-icon.svg';
import icon3 from '@assets/svg/empty-star.svg';
import type { Meta, StoryObj } from '@storybook/react';

import SVGCarouselModal from '@components/SVGCarouselModal/SVGCarouselModal';

const meta = {
  title: 'SVGCarouselModal',
  component: SVGCarouselModal,
  argTypes: {
    isOpen: { control: 'boolean' },
    closeModal: { control: false },
    carouselWidth: { control: 'number' },
    carouselHeight: { control: 'number' },
    showArrows: { control: 'boolean' },
    showDots: { control: 'boolean' },
    showNavigationOnHover: { control: 'boolean' },
    carouselImages: { control: false },
  },
  args: {
    isOpen: true,
    closeModal: () => {},
    carouselWidth: 450,
    carouselHeight: 450,
    showArrows: false,
    showDots: false,
    showNavigationOnHover: false,
  },
} satisfies Meta<typeof SVGCarouselModal>;

export default meta;
type Story = StoryObj<typeof SVGCarouselModal>;

const images = [icon1, icon2, icon3];

export const Default: Story = {
  render: ({ ...args }) => {
    return <SVGCarouselModal {...args} carouselImages={images} />;
  },
  args: {
    showArrows: true,
    showDots: true,
    modalWidth: 300,
    modalHeight: 300,
    buttonGap: 48,
  },
};
