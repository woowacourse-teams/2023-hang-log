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
  isShared: boolean;
}

const ExpenseInformation = ({ tripId, isShared }: ExpenseInformationProps) => {
  const navigate = useNavigate();

  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { expenseData } = useExpense(tripId);

  const goToTripPage = () => {
    if (!isShared) {
      navigate(PATH.TRIP(tripId));
      return;
    }

    navigate(PATH.SHARE_TRIP(tripId));
  };

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
        <Button variant="primary" size="small" onClick={goToTripPage}>
          여행 기록
        </Button>
      </Box>
    </header>
  );
};

export default ExpenseInformation;
