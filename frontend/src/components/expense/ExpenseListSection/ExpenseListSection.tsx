import { useRecoilValue } from 'recoil';

import { Flex, Heading, Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';

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

  const { selected: selectedFilter, handleSelectClick: handleFilterSelectClick } = useSelect(
    EXPENSE_LIST_FILTERS.DAY_LOG
  );

  return (
    <section css={containerStyling}>
      <Flex styles={{ justify: isMobile ? 'space-between' : 'flex-end' }}>
        {isMobile && <Heading size="xSmall">경비 상세 정보</Heading>}
        <ToggleGroup css={toggleGroupStyling}>
          <Toggle
            text={EXPENSE_LIST_FILTERS.DAY_LOG}
            toggleId={EXPENSE_LIST_FILTERS.DAY_LOG}
            selectedId={selectedFilter}
            changeSelect={handleFilterSelectClick}
            aria-label="날짜 필터"
          />
          <Toggle
            text={EXPENSE_LIST_FILTERS.CATEGORY}
            toggleId={EXPENSE_LIST_FILTERS.CATEGORY}
            selectedId={selectedFilter}
            changeSelect={handleFilterSelectClick}
            aria-label="카테고리 필터"
          />
        </ToggleGroup>
      </Flex>
      {selectedFilter === EXPENSE_LIST_FILTERS.DAY_LOG ? (
        <ExpenseDates tripId={tripId} tripType={tripType} />
      ) : (
        <ExpenseCategories tripId={tripId} tripType={tripType} />
      )}
    </section>
  );
};

export default ExpenseListSection;
