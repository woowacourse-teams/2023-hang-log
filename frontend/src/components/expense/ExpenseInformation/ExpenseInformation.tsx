import { useNavigate } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Badge, Box, Button, Heading, Text } from 'hang-log-design-system';

import {
  badgeWrapperStyling,
  buttonWrapperStyling,
  sectionStyling,
  titleStyling,
} from '@components/expense/ExpenseInformation/ExpenseInformation.style';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { formatDate } from '@utils/formatter';

import type { ExpenseData } from '@type/expense';

import { PATH } from '@constants/path';

interface ExpenseInformationProps
  extends Pick<ExpenseData, 'title' | 'startDate' | 'endDate' | 'cities'> {
  tripId: number;
}

const ExpenseInformation = ({ ...information }: ExpenseInformationProps) => {
  const navigate = useNavigate();

  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <header css={sectionStyling}>
      <Box tag="section">
        <Box css={badgeWrapperStyling}>
          {information.cities.map(({ id, name }) => (
            <Badge key={id}>{name}</Badge>
          ))}
        </Box>
        <Heading css={titleStyling} size={isMobile ? 'medium' : 'large'}>
          {information.title}
        </Heading>
        <Text>
          {formatDate(information.startDate)} - {formatDate(information.endDate)}
        </Text>
      </Box>
      <Box css={buttonWrapperStyling}>
        <Button
          variant="primary"
          size="small"
          onClick={() => navigate(PATH.TRIP(information.tripId))}
        >
          여행 기록
        </Button>
      </Box>
    </header>
  );
};

export default ExpenseInformation;
