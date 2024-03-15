import { Button, Flex, Heading, useOverlay } from 'hang-log-design-system';
import { Suspense, useCallback, useEffect, useState } from 'react';

import CategoryAddModal from '@/category/components/CategoryAddModal/CategoryAddModal';
import CategoryTable from '@/category/components/CategoryTable/CategoryTable';
import CategoryTableSkeleton from '@/category/components/CategoryTable/CategoryTableSkeleton';
import { useCategoryQuery } from '@/category/hooks/useCategoryQuery';

import PageNavigation from '@components/common/PageNavigation/PageNavigation';
import SidebarNavigation from '@components/common/SidebarNavigation/SidebarNavigation';

import { usePageIndex } from '@hooks/usePageIndex';

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
            <Suspense fallback={<CategoryTableSkeleton length={TABLE_ROW_LENGTH} />}>
              <CategoryTable categories={currentPageData} />
            </Suspense>
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
