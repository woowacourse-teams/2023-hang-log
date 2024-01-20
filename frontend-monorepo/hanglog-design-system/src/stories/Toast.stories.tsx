import CheckCircleIcon from '@assets/svg/check-circle-icon.svg';
import { containerStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import Button from '@components/Button/Button';
import Toast from '@components/Toast/Toast';
import ToastContainer from '@components/ToastContainer/ToastContainer';

import { useOverlay } from '..';

const meta = {
  title: 'Toast',
  component: Toast,
  argTypes: {
    variant: {
      control: { type: 'radio' },
      options: ['default', 'success', 'error'],
    },
    hasCloseButton: {
      control: { type: 'boolean' },
    },
    showDuration: {
      control: { type: 'number' },
    },
  },
  args: {
    variant: 'default',
    hasCloseButton: false,
    showDuration: 5000,
    children: 'Some message',
  },
  decorators: [
    (Story) => (
      <div css={containerStyle}>
        <ToastContainer />
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof Toast>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Variants: Story = {
  render: ({ children, ...args }) => {
    const { isOpen, open } = useOverlay();

    setTimeout(() => {
      open();
    }, 300);

    return (
      <div>
        {isOpen && (
          <>
            <Toast variant="default" {...args}>
              {children}
            </Toast>
            <Toast variant="success" {...args}>
              {children}
            </Toast>
            <Toast variant="error" {...args}>
              {children}
            </Toast>
          </>
        )}
      </div>
    );
  },
  argTypes: {
    variant: {
      control: false,
    },
  },
  args: {
    showDuration: 100000,
  },
};

export const Default: Story = {
  render: ({ children, ...args }) => {
    const { isOpen, open, close } = useOverlay();

    return (
      <>
        <Button onClick={open}>Show Toast</Button>
        {isOpen && (
          <Toast {...args} onClose={close}>
            {children}
          </Toast>
        )}
      </>
    );
  },
};

export const Success: Story = {
  render: ({ children, ...args }) => {
    const { isOpen, open } = useOverlay();

    return (
      <>
        <Button onClick={open}>Show Toast</Button>
        {isOpen && <Toast {...args}>{children}</Toast>}
      </>
    );
  },
  args: {
    variant: 'success',
  },
};

export const Error: Story = {
  render: ({ children, ...args }) => {
    const { isOpen, open } = useOverlay();

    return (
      <>
        <Button onClick={open}>Show Toast</Button>
        {isOpen && <Toast {...args}>{children}</Toast>}
      </>
    );
  },
  args: {
    variant: 'error',
  },
};

export const ClosableToast: Story = {
  render: ({ children, ...args }) => {
    const { isOpen, open, close } = useOverlay();

    return (
      <>
        <Button onClick={open}>Show Toast</Button>
        {isOpen && (
          <Toast {...args} onClose={close}>
            {children}
          </Toast>
        )}
      </>
    );
  },
  args: {
    hasCloseButton: true,
  },
};

export const WithIcon: Story = {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  render: ({ children, ...args }) => {
    const { isOpen, open, close } = useOverlay();

    return (
      <>
        <Button onClick={open}>Show Toast</Button>
        {isOpen && (
          <Toast {...args} onClose={close}>
            <CheckCircleIcon />
            Message
          </Toast>
        )}
      </>
    );
  },
  args: {
    hasCloseButton: true,
    showDuration: 1000000,
  },
  name: 'Toast with Icon',
};

export const PilingToast: Story = {
  render: ({ children, ...args }) => {
    const { isOpen, open, close } = useOverlay();
    const { isOpen: isOpen2, open: open2, close: close2 } = useOverlay();

    return (
      <>
        <Button onClick={open}>Show Toast 1</Button>
        {isOpen && (
          <Toast {...args} onClose={close}>
            {children}
          </Toast>
        )}
        <Button onClick={open2}>Show Toast 2</Button>
        {isOpen2 && (
          <Toast {...args} onClose={close2}>
            {children}
          </Toast>
        )}
      </>
    );
  },
  args: {
    hasCloseButton: true,
  },
};
