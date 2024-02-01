import { useCallback, useState } from 'react';

import { INDEX_UNIT_LENGTH } from '@constants/ui';

export const usePageIndex = (lastPageIndex: number, page: number) => {
  const countUnit = lastPageIndex <= INDEX_UNIT_LENGTH ? lastPageIndex : INDEX_UNIT_LENGTH;

  const [pageIndexDatas, setPageIndexDatas] = useState<number[]>(
    Array.from({ length: countUnit }, (_, index) => index)
  );

  const centerIndex = Math.floor(countUnit / 2);
  const isCurrentPageAfterCenter = page > centerIndex;
  const isCurrentPageBeforeCenter = page >= lastPageIndex - centerIndex;
  const isCurrentPageCenter = page < lastPageIndex - centerIndex;

  const changeNavigationDatas = useCallback(() => {
    if (isCurrentPageAfterCenter) {
      setPageIndexDatas(Array.from({ length: countUnit }, (_, index) => index));
    }

    if (isCurrentPageBeforeCenter) {
      const lastIndex = lastPageIndex;

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
    lastPageIndex,
    isCurrentPageAfterCenter,
    isCurrentPageBeforeCenter,
    isCurrentPageCenter,
    page,
  ]);

  return { pageIndexDatas, changeNavigationDatas };
};
