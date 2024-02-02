import { Flex, Heading, Button, Skeleton } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';

import CityTableSkeleton from '@/components/city/CityTable/CityTableSkeleton';

import {
  containerStyling,
  titleStyling,
  addButtonStyling,
  tableStyling,
  pagenationSkeletonStyling,
} from './CityPage.style';

const CityPageSkeleton = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
          <Heading size="large" css={titleStyling}>
            도시 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling}>
            추가하기
          </Button>
          <section css={tableStyling}>
            <CityTableSkeleton length={10} />
          </section>
          <div css={pagenationSkeletonStyling}>
            <Skeleton />
          </div>
        </Flex>
      </Flex>
    </>
  );
};

export default CityPageSkeleton;
