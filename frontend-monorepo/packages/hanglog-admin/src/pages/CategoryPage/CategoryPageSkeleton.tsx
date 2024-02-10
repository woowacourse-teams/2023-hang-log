import { Button, Flex, Heading, Skeleton } from 'hang-log-design-system';

import SidebarNavigation from '@components/common/SidebarNavigation/SidebarNavigation';
import CategoryTableSkeleton from '@components/category/CategoryTable/CategoryTableSkeleton';

import {
  containerStyling,
  titleStyling,
  addButtonStyling,
  tableStyling,
  pagenationSkeletonStyling,
} from './CategoryPage.style';

const CategoryPageSkeleton = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
          <Heading size="large" css={titleStyling}>
            카테고리 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <CategoryTableSkeleton length={10} />
          </section>
          <div css={pagenationSkeletonStyling}>
            <Skeleton />
          </div>
        </Flex>
      </Flex>
    </>
  );
};

export default CategoryPageSkeleton;
