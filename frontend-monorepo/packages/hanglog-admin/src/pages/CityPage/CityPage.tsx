import { useCallback, useEffect, useState } from 'react';

import { Button, Flex, Heading } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';
import PageNavigation from '@/components/common/PageNavigation/PageNavigation';
import CityTable from '@/components/city/CityTable/CityTable';

import { useCityQuery } from '@/hooks/api/useCityQuery';
import { usePageIndex } from '@/hooks/common/usePageIndex';

import { TABLE_ROW_LENGTH } from '@/constants/ui';

import { containerStyling, titleStyling, addButtonStyling, tableStyling } from './CityPage.style';

const CityPage = () => {
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
          <Button variant="primary" css={addButtonStyling}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <CityTable cities={currentPageData} />
          </section>
          <PageNavigation
            pages={pageIndexDatas}
            selected={page}
            maxPage={lastPageIndex}
            onChangeNavigate={handleSetPage}
          />
        </Flex>
      </Flex>
    </>
  );
};

export default CityPage;
