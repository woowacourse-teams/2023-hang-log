import { Flex, Heading } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';

const CategoryPage = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Heading>행록 카테고리 관리 페이지 입니다.</Heading>
      </Flex>
    </>
  );
};

export default CategoryPage;
