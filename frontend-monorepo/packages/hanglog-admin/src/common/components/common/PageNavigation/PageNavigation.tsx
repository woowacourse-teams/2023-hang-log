import { Box, Flex } from 'hang-log-design-system';

import {
  BoxStyling,
  ContainerStyling,
  LeftButtonStyling,
  RightButtonStyling,
  SelectedBoxStyling,
} from '@components/common/PageNavigation/PageNavigation.style';

import LeftButton from '@assets/svg/left-button.svg?react';
import RightButton from '@assets/svg/right-button.svg?react';

interface PageNavigationProps {
  pages: number[];
  selected: number;
  maxPage: number;
  onChangeNavigate: (page: number) => void;
}

const PageNavigation = ({ pages, selected, maxPage, onChangeNavigate }: PageNavigationProps) => {
  const nextUnit = selected === maxPage ? selected : selected + 1;
  const previousUnit = selected === 1 ? selected : selected - 1;

  return (
    <Flex styles={{ justify: 'center', align: 'center' }} css={ContainerStyling}>
      <Box
        css={LeftButtonStyling}
        onClick={() => {
          onChangeNavigate(previousUnit);
        }}
      >
        <LeftButton />
      </Box>
      {pages.map((item) => {
        return (
          <Box
            css={[BoxStyling, selected === item && SelectedBoxStyling]}
            key={item}
            onClick={() => {
              onChangeNavigate(item);
            }}
          >
            {item}
          </Box>
        );
      })}
      <Box
        css={RightButtonStyling}
        onClick={() => {
          onChangeNavigate(nextUnit);
        }}
      >
        <RightButton />
      </Box>
    </Flex>
  );
};

export default PageNavigation;
