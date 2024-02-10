import { Button, Flex, Heading, useOverlay } from 'hang-log-design-system';
import { useCallback, useEffect, useState } from 'react';

import CategoryAddModal from '@components/category/CategoryAddModal/CategoryAddModal';
import CategoryTable from '@components/category/CategoryTable/CategoryTable';
import PageNavigation from '@components/common/PageNavigation/PageNavigation';
import SidebarNavigation from '@components/common/SidebarNavigation/SidebarNavigation';

import { useCategoryQuery } from '@hooks/api/useCategoryQuery';
import { usePageIndex } from '@hooks/common/usePageIndex';

import { TABLE_ROW_LENGTH } from '@constants/ui';

import {
  addButtonStyling,
  containerStyling,
  tableStyling,
  titleStyling,
} from './CategoryPage.style';

const CategoryPage = () => {
  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();

  const [page, setPage] = useState<number>(1);

  const { categoryData } = useCategoryQuery();

  const lastPageIndex = Math.ceil(categoryData.length / TABLE_ROW_LENGTH);
  const currentPageData = categoryData.slice(
    (page - 1) * TABLE_ROW_LENGTH,
    page * TABLE_ROW_LENGTH
  );

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
            카테고리 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling} onClick={openAddModal}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <CategoryTable categories={currentPageData} />
          </section>
          <PageNavigation
            pages={pageIndexDatas}
            selected={page}
            maxPage={lastPageIndex}
            onChangeNavigate={handleSetPage}
          />
        </Flex>
        {isAddModalOpen && <CategoryAddModal onClose={closeAddModal} />}
      </Flex>
    </>
  );
};

export default CategoryPage;
