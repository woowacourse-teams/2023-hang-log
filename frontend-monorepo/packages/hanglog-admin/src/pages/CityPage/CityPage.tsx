import { useCallback, useEffect, useState } from 'react';

import { Button, Flex, Heading } from 'hang-log-design-system';

import { containerStyling } from './CityPage.style';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';
import PageNavigation from '@/components/common/PageNavigation/PageNavigation';
import CityTable from '@/components/city/CityTable/CityTable';

import { useCityQuery } from '@/hooks/api/useCityQuery';
import { usePageIndex } from '@/hooks/common/usePageIndex';

import { TABLE_ROW_LENGTH } from '@/constants/ui';

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
        <section css={containerStyling}>
          <Heading size="large">도시 관리</Heading>
          <Button> 추가하기 </Button>
          <CityTable cities={currentPageData} />
          <PageNavigation
            pages={pageIndexDatas}
            selected={page}
            maxPage={lastPageIndex}
            onChangeNavigate={handleSetPage}
          />
        </section>
      </Flex>
    </>
  );
};

export default CityPage;
