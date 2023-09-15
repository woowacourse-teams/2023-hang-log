import { useCallback, useEffect, useState } from 'react';

import { Text } from 'hang-log-design-system';

import { subTitleStyling } from '@pages/CommunityPage/CommunityPage.style';

import PageNavigation from '@components/common/PageNavigation/PageNavigation';
import CommunityHeader from '@components/community/CommunityHeader/CommunityHeader';
import CommunityTripItemList from '@components/trips/CommunityTripItemList/CommunityTripItemList';

import { useCommunityTripsQuery } from '@hooks/api/useCommunityTripsQuery';
import { useRecommendedTripsQuery } from '@hooks/api/useRecommendedTripsQuery';

import { TRIP_INDEX_UNIT_LENGTH } from '@constants/ui';

const CommunityPage = () => {
  const [page, setPage] = useState<number>(1);
  const [pageIndexDatas, setPageIndexDatas] = useState<number[]>(
    Array.from({ length: TRIP_INDEX_UNIT_LENGTH }, (_, index) => index)
  );

  const { tripsData: recommendedTripsData } = useRecommendedTripsQuery();
  const { tripsData: communityTripsData } = useCommunityTripsQuery(page, 10);

  const handleSetPage = useCallback((page: number) => {
    setPage(page);
  }, []);

  useEffect(() => {
    const centerIndex = Math.floor(TRIP_INDEX_UNIT_LENGTH / 2);
    const validateFirstList = page > centerIndex;
    const validateLastList = page >= communityTripsData.lastPageIndex - centerIndex;
    const validateMiddleList = page < communityTripsData.lastPageIndex - centerIndex;

    if (validateFirstList) {
      setPageIndexDatas(Array.from({ length: TRIP_INDEX_UNIT_LENGTH }, (_, index) => index));
    }

    if (validateLastList) {
      const lastIndex = communityTripsData.lastPageIndex;

      const refreshedIndexDatas = Array.from(
        { length: TRIP_INDEX_UNIT_LENGTH },
        (_, index) => lastIndex - index
      ).reverse();

      setPageIndexDatas(refreshedIndexDatas);
    }

    if (validateMiddleList) {
      const halfLength = Math.floor(TRIP_INDEX_UNIT_LENGTH / 2);
      const startPage = Math.max(page - halfLength, 1);

      const refreshedIndexDatas = Array.from(
        { length: TRIP_INDEX_UNIT_LENGTH },
        (_, index) => startPage + index
      );

      setPageIndexDatas(refreshedIndexDatas);
    }
  }, [communityTripsData.lastPageIndex, page]);

  return (
    <>
      <CommunityHeader />
      <Text css={subTitleStyling(32, 8)}>{recommendedTripsData.title}</Text>
      <CommunityTripItemList trips={recommendedTripsData.trips} />
      <Text css={subTitleStyling(57, 8)}>더 많은 여행들을 확인해보세요</Text>
      <CommunityTripItemList trips={communityTripsData.trips} />
      <PageNavigation
        pages={pageIndexDatas}
        selected={page}
        maxPage={communityTripsData.lastPageIndex}
        onChangeNavigate={handleSetPage}
      />
    </>
  );
};

export default CommunityPage;
