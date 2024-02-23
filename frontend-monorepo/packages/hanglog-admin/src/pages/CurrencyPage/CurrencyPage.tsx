import { Button, Flex, Heading, useOverlay } from 'hang-log-design-system';
import { Suspense, useCallback, useEffect, useState } from 'react';

import PageNavigation from '@components/common/PageNavigation/PageNavigation';
import SidebarNavigation from '@components/common/SidebarNavigation/SidebarNavigation';
import CurrencyAddModal from '@components/currency/CurrencyAddModal/CurrencyAddModal';
import CurrencyTable from '@components/currency/CurrencyTable/CurrencyTable';
import CurrencyTableSkeleton from '@components/currency/CurrencyTable/CurrencyTableSkeleton';

import { useCurrencyQuery } from '@hooks/api/useCurrencyQuery';
import { usePageIndex } from '@hooks/common/usePageIndex';

import { TABLE_ROW_LENGTH } from '@constants/ui';

import {
  addButtonStyling,
  containerStyling,
  tableStyling,
  titleStyling,
} from './CurrencyPage.style';

const CurrencyPage = () => {
  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();

  const [page, setPage] = useState<number>(1);

  const { currencyListData } = useCurrencyQuery(page, TABLE_ROW_LENGTH);

  const { pageIndexDatas, changeNavigationDatas } = usePageIndex(
    currencyListData.lastPageIndex,
    page
  );

  const handleSetPage = useCallback((page: number) => {
    setPage(page);
  }, []);

  useEffect(() => {
    changeNavigationDatas();
  }, [changeNavigationDatas, page]);

  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
          <Heading size="large" css={titleStyling}>
            환율 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling} onClick={openAddModal}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <Suspense fallback={<CurrencyTableSkeleton length={TABLE_ROW_LENGTH} />}>
              <CurrencyTable currencies={currencyListData.currencies} />
            </Suspense>
          </section>
          <PageNavigation
            pages={pageIndexDatas}
            selected={page}
            maxPage={currencyListData.lastPageIndex}
            onChangeNavigate={handleSetPage}
          />
          {isAddModalOpen && <CurrencyAddModal onClose={closeAddModal} />}
        </Flex>
      </Flex>
    </>
  );
};

export default CurrencyPage;
