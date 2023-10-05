import { useCallback, useState } from 'react';

import type { CommunityTripsData } from '@type/trips';

import { TRIP_INDEX_UNIT_LENGTH } from '@constants/ui';

export const useTripPageIndex = (data: CommunityTripsData, page: number, maxIndex: number) => {
  const countUnit = maxIndex <= TRIP_INDEX_UNIT_LENGTH ? maxIndex : TRIP_INDEX_UNIT_LENGTH;

  const [pageIndexDatas, setPageIndexDatas] = useState<number[]>(
    Array.from({ length: countUnit }, (_, index) => index)
  );

  const centerIndex = Math.floor(countUnit / 2);
  const isCurrentPageAfterCenter = page > centerIndex;
  const isCurrentPageBeforeCenter = page >= data.lastPageIndex - centerIndex;
  const isCurrentPageCenter = page < data.lastPageIndex - centerIndex;

  const changeNavigationDatas = useCallback(() => {
    if (isCurrentPageAfterCenter) {
      setPageIndexDatas(Array.from({ length: countUnit }, (_, index) => index));
    }

    if (isCurrentPageBeforeCenter) {
      const lastIndex = data.lastPageIndex;

      const refreshedIndexDatas = Array.from(
        { length: countUnit },
        (_, index) => lastIndex - index
      ).reverse();

      setPageIndexDatas(refreshedIndexDatas);
    }

    if (isCurrentPageCenter) {
      const halfLength = Math.floor(countUnit / 2);
      const startPage = Math.max(page - halfLength, 1);

      const refreshedIndexDatas = Array.from(
        { length: countUnit },
        (_, index) => startPage + index
      );

      setPageIndexDatas(refreshedIndexDatas);
    }
  }, [
    data.lastPageIndex,
    isCurrentPageAfterCenter,
    isCurrentPageBeforeCenter,
    isCurrentPageCenter,
    page,
  ]);

  return { pageIndexDatas, changeNavigationDatas };
};
