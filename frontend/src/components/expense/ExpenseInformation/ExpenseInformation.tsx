import { useNavigate } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Badge, Box, Button, Heading, Text } from 'hang-log-design-system';

import {
  badgeWrapperStyling,
  buttonWrapperStyling,
  sectionStyling,
  titleStyling,
} from '@components/expense/ExpenseInformation/ExpenseInformation.style';

import { useExpense } from '@hooks/expense/useExpense';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { formatDate } from '@utils/formatter';

import { PATH } from '@constants/path';

interface ExpenseInformationProps {
  tripId: number;
}

const ExpenseInformation = ({ tripId }: ExpenseInformationProps) => {
  const navigate = useNavigate();

  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { expenseData } = useExpense(tripId);

  return (
    <header css={sectionStyling}>
      <Box tag="section">
        <Box css={badgeWrapperStyling}>
          {expenseData.cities.map(({ id, name }) => (
            <Badge key={id}>{name}</Badge>
          ))}
        </Box>
        <Heading css={titleStyling} size={isMobile ? 'medium' : 'large'}>
          {expenseData.title}
        </Heading>
        <Text>
          {formatDate(expenseData.startDate)} - {formatDate(expenseData.endDate)}
        </Text>
      </Box>
      <Box css={buttonWrapperStyling}>
        <Button variant="primary" size="small" onClick={() => navigate(PATH.TRIP(tripId))}>
          여행 기록
        </Button>
      </Box>
    </header>
  );
};

export default ExpenseInformation;
