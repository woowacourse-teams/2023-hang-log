import { Flex, Heading } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';

import { subTitleStyling } from './AdminMainPage.style';

const AdminMainPage = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Heading css={subTitleStyling({ topPadding: 50, bottomPadding: 8 })}>
          행록 관리자 페이지에 오신 것을 환영합니다.
        </Heading>
      </Flex>
    </>
  );
};

export default AdminMainPage;
