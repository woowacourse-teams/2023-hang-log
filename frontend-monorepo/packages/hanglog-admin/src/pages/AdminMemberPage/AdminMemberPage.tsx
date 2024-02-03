import { useCallback, useEffect, useState } from 'react';

import { Button, Flex, Heading, useOverlay } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';
import PageNavigation from '@/components/common/PageNavigation/PageNavigation';
import AdminMemberTable from '@/components/adminMember/AdminMemberTable/AdminMemberTable';

import { useAdminMemberQuery } from '@/hooks/api/useAdminMemberQuery';
import { usePageIndex } from '@/hooks/common/usePageIndex';

import { TABLE_ROW_LENGTH } from '@/constants/ui';

import {
  containerStyling,
  titleStyling,
  addButtonStyling,
  tableStyling,
} from './AdminMemberPage.style';

const AdminMemberPage = () => {
  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();

  const [page, setPage] = useState<number>(1);

  const { adminMemberData } = useAdminMemberQuery();

  const lastPageIndex = Math.ceil(adminMemberData.length / TABLE_ROW_LENGTH);
  const currentPageData = adminMemberData.slice(
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
            관리자 멤버 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling} onClick={openAddModal}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <AdminMemberTable adminMembers={currentPageData} />
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

export default AdminMemberPage;
