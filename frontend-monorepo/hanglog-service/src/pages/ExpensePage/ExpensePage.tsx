import { useParams } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Divider, Flex } from 'hang-log-design-system';

import { containerStyling, dividerStyling } from '@pages/ExpensePage/ExpensePage.style';

import ExpenseListSection from '@components/expense/ExpenseListSection/ExpenseListSection';
import TotalExpenseSection from '@components/expense/TotalExpenseSection/TotalExpenseSection';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

import { mediaQueryMobileState } from '@store/mediaQuery';

import type { TripTypeData } from '@type/trip';

interface ExpensePageProps {
  tripType?: TripTypeData;
}

const ExpensePage = ({ tripType = 'PERSONAL' }: ExpensePageProps) => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다.');

  const isMobile = useRecoilValue(mediaQueryMobileState);

  useExpenseQuery(tripId, tripType);

  return (
    <Flex css={containerStyling}>
      <TotalExpenseSection tripId={tripId} tripType={tripType} />
      {isMobile && <Divider css={dividerStyling} />}
      <ExpenseListSection tripId={tripId} tripType={tripType} />
    </Flex>
  );
};

export default ExpensePage;
