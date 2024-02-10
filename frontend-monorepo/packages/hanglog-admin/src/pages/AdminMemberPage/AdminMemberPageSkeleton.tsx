import { Button, Flex, Heading, Skeleton } from 'hang-log-design-system';

import AdminMemberTableSkeleton from '@components/adminMember/AdminMemberTable/AdminMemberTableSkeleton';
import SidebarNavigation from '@components/common/SidebarNavigation/SidebarNavigation';

import {
  addButtonStyling,
  containerStyling,
  pagenationSkeletonStyling,
  tableStyling,
  titleStyling,
} from './AdminMemberPage.style';

const AdminMemberPageSkeleton = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
          <Heading size="large" css={titleStyling}>
            관리자 멤버 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <AdminMemberTableSkeleton length={10} />
          </section>
          <div css={pagenationSkeletonStyling}>
            <Skeleton />
          </div>
        </Flex>
      </Flex>
    </>
  );
};

export default AdminMemberPageSkeleton;
