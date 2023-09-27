import { useCallback, useEffect, useState } from 'react';

import { useRecoilValue } from 'recoil';

import { Text } from 'hang-log-design-system';

import { subTitleStyling } from '@pages/CommunityPage/CommunityPage.style';

import PageNavigation from '@components/common/PageNavigation/PageNavigation';
import CommunityHeader from '@components/community/CommunityHeader/CommunityHeader';
import CommunityTripItemList from '@components/trips/CommunityTripItemList/CommunityTripItemList';

import { useCommunityTripsQuery } from '@hooks/api/useCommunityTripsQuery';
import { useRecommendedTripsQuery } from '@hooks/api/useRecommendedTripsQuery';
import { useTripPageIndex } from '@hooks/common/usePageIndex';

import { isLoggedInState } from '@store/auth';

const CommunityPage = () => {
  const [page, setPage] = useState<number>(1);

  const isLoggedIn = useRecoilValue(isLoggedInState);

  const { tripsData: recommendedTripsData } = useRecommendedTripsQuery(isLoggedIn);
  const { tripsData: communityTripsData } = useCommunityTripsQuery(page, 10, isLoggedIn);

  const { pageIndexDatas, changeNavigationDatas } = useTripPageIndex(communityTripsData, page);

  const handleSetPage = useCallback((page: number) => {
    setPage(page);
  }, []);

  useEffect(() => {
    changeNavigationDatas();
  }, [changeNavigationDatas, page]);

  return (
    <>
      <CommunityHeader />
      <Text css={subTitleStyling({ topPadding: 32, bottomPadding: 8 })}>
        {recommendedTripsData.title}
      </Text>
      <CommunityTripItemList trips={recommendedTripsData.trips} />
      <Text css={subTitleStyling({ topPadding: 57, bottomPadding: 8 })}>
        더 많은 여행들을 확인해보세요
      </Text>
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
