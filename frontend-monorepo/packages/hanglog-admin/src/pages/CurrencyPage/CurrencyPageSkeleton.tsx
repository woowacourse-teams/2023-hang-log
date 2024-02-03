import { Button, Flex, Heading, Skeleton } from 'hang-log-design-system';

import SidebarNavigation from '@/components/common/SidebarNavigation/SidebarNavigation';
import CurrencyTableSkeleton from '@/components/currency/CurrencyTable/CurrencyTableSkeleton';

import {
  containerStyling,
  titleStyling,
  addButtonStyling,
  tableStyling,
  pagenationSkeletonStyling,
} from './CurrencyPage.style';
import CurrencyAddModal from '@/components/currency/CurrencyAddModal/CurrencyAddModal';

const CurrencyPageSkeleton = () => {
  return (
    <>
      <Flex>
        <SidebarNavigation />
        <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
          <Heading size="large" css={titleStyling}>
            환율 관리
          </Heading>
          <Button variant="primary" css={addButtonStyling}>
            추가하기
          </Button>

          <section css={tableStyling}>
            <CurrencyTableSkeleton length={10} />
          </section>
          <div css={pagenationSkeletonStyling}>
            <Skeleton />
          </div>
        </Flex>
      </Flex>
    </>
  );
};

export default CurrencyPageSkeleton;
