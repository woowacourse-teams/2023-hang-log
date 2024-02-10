import { Button, Flex, Heading, Skeleton } from 'hang-log-design-system';

import CityTableSkeleton from '@components/city/CityTable/CityTableSkeleton';
import SidebarNavigation from '@components/common/SidebarNavigation/SidebarNavigation';

import {
  addButtonStyling,
  containerStyling,
  pagenationSkeletonStyling,
  tableStyling,
  titleStyling,
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
