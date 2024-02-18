import { useRecoilValue } from 'recoil';

import { Flex, Heading, NewToggle as Toggle } from 'hang-log-design-system';

import ExpenseCategories from '@components/expense/ExpenseCategories/ExpenseCategories';
import ExpenseDates from '@components/expense/ExpenseDates/ExpenseDates';
import {
  containerStyling,
  toggleGroupStyling,
} from '@components/expense/ExpenseListSection/ExpenseListSection.style';

import { mediaQueryMobileState } from '@store/mediaQuery';

import type { TripTypeData } from '@type/trip';

import { EXPENSE_LIST_FILTERS } from '@constants/expense';

interface ExpenseListProps {
  tripId: string;
  tripType: TripTypeData;
}

const ExpenseListSection = ({ tripId, tripType }: ExpenseListProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <Toggle initialSelect={EXPENSE_LIST_FILTERS.DAY_LOG}>
      <section css={containerStyling}>
        <Flex styles={{ justify: isMobile ? 'space-between' : 'flex-end' }}>
          {isMobile && <Heading size="xSmall">경비 상세 정보</Heading>}

          <Flex css={toggleGroupStyling}>
            <Toggle.List
              text={EXPENSE_LIST_FILTERS.DAY_LOG}
              toggleKey={EXPENSE_LIST_FILTERS.DAY_LOG}
              aria-label="날짜 필터"
            />
            <Toggle.List
              text={EXPENSE_LIST_FILTERS.CATEGORY}
              toggleKey={EXPENSE_LIST_FILTERS.CATEGORY}
              aria-label="카테고리 필터"
            />
          </Flex>
        </Flex>
        <Toggle.Item toggleKey={EXPENSE_LIST_FILTERS.DAY_LOG}>
          <ExpenseDates tripId={tripId} tripType={tripType} />
        </Toggle.Item>
        <Toggle.Item toggleKey={EXPENSE_LIST_FILTERS.CATEGORY}>
          <ExpenseCategories tripId={tripId} tripType={tripType} />
        </Toggle.Item>
      </section>
    </Toggle>
  );
};

export default ExpenseListSection;
