import { Button, Flex, Heading, useOverlay } from 'hang-log-design-system';
import { Suspense, useCallback, useEffect, useState } from 'react';

import CityAddModal from '@components/city/CityAddModal/CityAddModal';
import CityTable from '@components/city/CityTable/CityTable';
import CityTableSkeleton from '@components/city/CityTable/CityTableSkeleton';
import PageNavigation from '@components/common/PageNavigation/PageNavigation';
import SidebarNavigation from '@components/common/SidebarNavigation/SidebarNavigation';

import { useCityQuery } from '@hooks/api/useCityQuery';
import { usePageIndex } from '@hooks/common/usePageIndex';

import { TABLE_ROW_LENGTH } from '@constants/ui';

import { addButtonStyling, containerStyling, tableStyling, titleStyling } from './CityPage.style';

const CityPage = () => {
  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();

  const [page, setPage] = useState<number>(1);

  const { cityData } = useCityQuery();

  const lastPageIndex = Math.ceil(cityData.length / TABLE_ROW_LENGTH);
  const currentPageData = cityData.slice((page - 1) * TABLE_ROW_LENGTH, page * TABLE_ROW_LENGTH);

  const { pageIndexDatas, changeNavigationDatas } = usePageIndex(lastPageIndex, page);

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
            도시 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling} onClick={openAddModal}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <Suspense fallback={<CityTableSkeleton length={TABLE_ROW_LENGTH} />}>
              <CityTable cities={currentPageData} />
            </Suspense>
          </section>
          <PageNavigation
            pages={pageIndexDatas}
            selected={page}
            maxPage={lastPageIndex}
            onChangeNavigate={handleSetPage}
          />
        </Flex>
        {isAddModalOpen && <CityAddModal onClose={closeAddModal} />}
      </Flex>
    </>
  );
};

export default CityPage;
