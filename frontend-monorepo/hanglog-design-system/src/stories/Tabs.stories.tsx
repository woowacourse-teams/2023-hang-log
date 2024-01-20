import { containerStyle, informationStyle } from '@stories/styles';
import type { Meta, StoryObj } from '@storybook/react';

import { useSelect } from '@hooks/useSelect';

import Tab from '@components/Tab/Tab';
import Tabs from '@components/Tabs/Tabs';

const meta = {
  title: 'Tabs',
  component: Tabs,
  decorators: [
    (Story) => (
      <ul css={containerStyle}>
        <Story />
      </ul>
    ),
  ],
} satisfies Meta<typeof Tabs>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Variants: Story = {
  render: () => {
    const outlineSelector = useSelect('tab1');
    const selectedOutline = outlineSelector.selected;
    const handleSelectOutline = outlineSelector.handleSelectClick;

    const blockSelector = useSelect('tab1');
    const selectedBlock = blockSelector.selected;
    const handleSelectBlock = blockSelector.handleSelectClick;

    const outlineSelectorOverflow = useSelect('tab1');
    const selectedOutlineOverflow = outlineSelectorOverflow.selected;
    const handleSelectOutlineOverflow = outlineSelectorOverflow.handleSelectClick;

    return (
      <>
        <li css={informationStyle}>
          <h6>outline</h6>
          <Tabs>
            <Tab
              text="Tab 1"
              variant="outline"
              tabId="tab1"
              changeSelect={handleSelectOutline}
              selectedId={selectedOutline}
            />
            <Tab
              text="Tab 2"
              variant="outline"
              tabId="tab2"
              changeSelect={handleSelectOutline}
              selectedId={selectedOutline}
            />
          </Tabs>
        </li>
        <li css={informationStyle}>
          <h6>block</h6>
          <Tabs>
            <Tab
              text="Tab 1"
              variant="block"
              tabId="tab1"
              changeSelect={handleSelectBlock}
              selectedId={selectedBlock}
            />
            <Tab
              text="Tab 2"
              variant="block"
              tabId="tab2"
              changeSelect={handleSelectBlock}
              selectedId={selectedBlock}
            />
          </Tabs>
        </li>
        <li css={informationStyle}>
          <h6>overflow-outline</h6>
          <Tabs>
            <Tab
              text="Tab 1"
              variant="outline"
              tabId="tab1"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 2"
              variant="outline"
              tabId="tab2"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 3"
              variant="outline"
              tabId="tab3"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 4"
              variant="outline"
              tabId="tab4"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 5"
              variant="outline"
              tabId="tab5"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 6"
              variant="outline"
              tabId="tab6"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 7"
              variant="outline"
              tabId="tab7"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 8"
              variant="outline"
              tabId="tab8"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 9"
              variant="outline"
              tabId="tab9"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 10"
              variant="outline"
              tabId="tab10"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 11"
              variant="outline"
              tabId="tab11"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 12"
              variant="outline"
              tabId="tab12"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 13"
              variant="outline"
              tabId="tab13"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab14"
              variant="outline"
              tabId="tab14"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 15"
              variant="outline"
              tabId="tab15"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 16"
              variant="outline"
              tabId="tab16"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 17"
              variant="outline"
              tabId="tab17"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 18"
              variant="outline"
              tabId="tab18"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 19"
              variant="outline"
              tabId="tab19"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 20"
              variant="outline"
              tabId="tab20"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 21"
              variant="outline"
              tabId="tab21"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 22"
              variant="outline"
              tabId="tab22"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 23"
              variant="outline"
              tabId="tab23"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 24"
              variant="outline"
              tabId="tab24"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
            <Tab
              text="Tab 25"
              variant="outline"
              tabId="tab25"
              changeSelect={handleSelectOutlineOverflow}
              selectedId={selectedOutlineOverflow}
            />
          </Tabs>
        </li>
      </>
    );
  },
};
