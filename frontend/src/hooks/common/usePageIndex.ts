import { useCallback, useState } from 'react';

import type { CommunityTripsData } from '@type/trips';

import { TRIP_INDEX_UNIT_LENGTH } from '@constants/ui';

export const useTripPageIndex = (data: CommunityTripsData, page: number) => {
  const [pageIndexDatas, setPageIndexDatas] = useState<number[]>(
    Array.from({ length: TRIP_INDEX_UNIT_LENGTH }, (_, index) => index)
  );

  const centerIndex = Math.floor(TRIP_INDEX_UNIT_LENGTH / 2);
  const isCurrentPageAfterCenter = page > centerIndex;
  const isCurrentPageBeforeCenter = page >= data.lastPageIndex - centerIndex;
  const isCurrentPageCenter = page < data.lastPageIndex - centerIndex;

  const changeNavigationDatas = useCallback(() => {
    if (isCurrentPageAfterCenter) {
      setPageIndexDatas(Array.from({ length: TRIP_INDEX_UNIT_LENGTH }, (_, index) => index));
    }

    if (isCurrentPageBeforeCenter) {
      const lastIndex = data.lastPageIndex;

      const refreshedIndexDatas = Array.from(
        { length: TRIP_INDEX_UNIT_LENGTH },
        (_, index) => lastIndex - index
      ).reverse();

      setPageIndexDatas(refreshedIndexDatas);
    }

    if (isCurrentPageCenter) {
      const halfLength = Math.floor(TRIP_INDEX_UNIT_LENGTH / 2);
      const startPage = Math.max(page - halfLength, 1);

      const refreshedIndexDatas = Array.from(
        { length: TRIP_INDEX_UNIT_LENGTH },
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
