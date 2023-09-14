import { Box, Flex } from 'hang-log-design-system';

import {
  BoxStyling,
  ContainerStyling,
  LeftButtonStyling,
  RightButtonStyling,
  SelectedBoxStyling,
} from '@components/common/PageNavigation/PageNavigation.style';

import LeftButton from '@assets/svg/left-button.svg';
import RightButton from '@assets/svg/right-button.svg';

interface PageNavigationProps {
  pages: number[];
  selected: number;
  maxPage: number;
  onChangeNavigate: (page: number) => void;
}

const PageNavigation = ({ pages, selected, maxPage, onChangeNavigate }: PageNavigationProps) => {
  const IncreaseNavigateUnit = selected === maxPage ? selected : selected + 1;
  const DecreaseNavigateUnit = selected === 1 ? selected : selected - 1;

  return (
    <Flex styles={{ justify: 'center', align: 'center' }} css={ContainerStyling}>
      <Box
        css={LeftButtonStyling}
        onClick={() => {
          onChangeNavigate(DecreaseNavigateUnit);
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
          onChangeNavigate(IncreaseNavigateUnit);
        }}
      >
        <RightButton />
      </Box>
    </Flex>
  );
};

export default PageNavigation;
