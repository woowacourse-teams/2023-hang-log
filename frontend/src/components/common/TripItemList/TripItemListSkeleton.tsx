import { TRIP_ITEM_LIST_SKELETON_LENGTH } from '@constants/ui';
import { Divider } from 'hang-log-design-system';
import { Fragment } from 'react';

import TripItemSkeleton from '@components/common/TripItem/TripItemSkeleton';
import { containerStyling } from '@components/common/TripItemList/TripItemList.style';

const TripItemListSkeleton = () => {
  return (
    <ol css={containerStyling}>
      {Array.from({ length: TRIP_ITEM_LIST_SKELETON_LENGTH }, (_, index) => (
        <Fragment key={index}>
          <TripItemSkeleton />
          <Divider />
        </Fragment>
      ))}
    </ol>
  );
};

export default TripItemListSkeleton;
