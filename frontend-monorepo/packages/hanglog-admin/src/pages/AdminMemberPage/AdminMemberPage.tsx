import { Flex, Heading } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';

const AdminMemberPage = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Heading>행록 관리자 멤버 관리 페이지입니다.</Heading>
      </Flex>
    </>
  );
};

export default AdminMemberPage;
