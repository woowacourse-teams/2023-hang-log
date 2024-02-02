import { Flex, Heading } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';

const CurrencyPage = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Heading>행록 환율 관리 페이지 입니다.</Heading>
      </Flex>
    </>
  );
};

export default CurrencyPage;
