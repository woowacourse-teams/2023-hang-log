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

import type { TripTypeData } from '@type/trip';

import { PATH } from '@constants/path';
import { TRIP_TYPE } from '@constants/trip';

interface ExpenseInformationProps {
  tripId: string;
  tripType: TripTypeData;
}

const ExpenseInformation = ({ tripId, tripType }: ExpenseInformationProps) => {
  const navigate = useNavigate();

  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { expenseData } = useExpense(tripId, tripType);

  const goToTripPage = () => {
    if (tripType === TRIP_TYPE.PUBLISHED) {
      navigate(PATH.COMMUNITY_TRIP(tripId));
      return;
    }

    if (tripType === TRIP_TYPE.SHARED) {
      navigate(PATH.SHARE_TRIP(tripId));
      return;
    }

    navigate(PATH.TRIP(tripId));
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
