import App from '@/App';

import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import CommunityPageSkeleton from '@pages/CommunityPage/CommunityPageSkeleton';
import ExpensePageSkeleton from '@pages/ExpensePage/ExpensePageSkeleton';
import TripsPageSkeleton from '@pages/MyTripsPage/MyTripsPageSkeleton';
import NotFoundPage from '@pages/NotFoundPage/NotFoundPage';
import RedirectPage from '@pages/RedirectPage/RedirectPage';
import TripPageSkeleton from '@pages/TripPage/TripPageSkeleton';

import { mediaQueryMobileState } from '@store/mediaQuery';

import * as Lazy from '@router/lazy';

import type { TripTypeData } from '@type/trip';

import { PATH } from '@constants/path';
import { TRIP_TYPE } from '@constants/trip';

const AppRouter = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const router = createBrowserRouter([
    {
      path: PATH.ROOT,
      element: <App />,
      errorElement: <NotFoundPage />,
      children: [
        {
          path: '',
          element: (
            <Suspense fallback={<CommunityPageSkeleton />}>
              <Lazy.CommunityPage />
            </Suspense>
          ),
        },
        {
          path: PATH.MY_TRIPS,
          element: (
            <Suspense fallback={<TripsPageSkeleton />}>
              <Lazy.TripsPage />
            </Suspense>
          ),
        },
        {
          path: PATH.TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              {isMobile ? (
                <Lazy.TripMobilePage tripType={TRIP_TYPE.PERSONAL as TripTypeData} />
              ) : (
                <Lazy.TripPage />
              )}
            </Suspense>
          ),
        },
        {
          path: PATH.EDIT_TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              <Lazy.TripEditPage />
            </Suspense>
          ),
        },
        {
          path: PATH.CREATE_TRIP,
          element: (
            <Suspense>
              <Lazy.TripCreatePage />
            </Suspense>
          ),
        },
        {
          path: PATH.EXPENSE(':tripId'),
          element: (
            <Suspense fallback={<ExpensePageSkeleton />}>
              <Lazy.ExpensePage />
            </Suspense>
          ),
        },
        {
          path: PATH.REDIRECT,
          element: <RedirectPage />,
        },
        {
          path: PATH.LOGIN,
          element: (
            <Suspense>
              <Lazy.LogInPage />
            </Suspense>
          ),
        },
        {
          path: PATH.MY_PAGE,
          element: (
            <Suspense fallback={<div>LOADING</div>}>
              <Lazy.MyPage />
            </Suspense>
          ),
        },
        {
          path: PATH.SHARE_TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              {isMobile ? (
                <Lazy.TripMobilePage tripType={TRIP_TYPE.SHARED as TripTypeData} />
              ) : (
                <Lazy.SharedTripPage />
              )}
            </Suspense>
          ),
        },
        {
          path: PATH.SHARE_EXPENSE(':tripId'),
          element: (
            <Suspense fallback={<ExpensePageSkeleton />}>
              <Lazy.ExpensePage tripType={TRIP_TYPE.SHARED as TripTypeData} />
            </Suspense>
          ),
        },
        {
          path: PATH.COMMUNITY_TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              {isMobile ? (
                <Lazy.TripMobilePage tripType={TRIP_TYPE.PUBLISHED as TripTypeData} />
              ) : (
                <Lazy.CommunityTripPage />
              )}
            </Suspense>
          ),
        },
        {
          path: PATH.COMMUNITY_EXPENSE(':tripId'),
          element: (
            <Suspense fallback={<ExpensePageSkeleton />}>
              <Lazy.ExpensePage tripType={TRIP_TYPE.PUBLISHED as TripTypeData} />
            </Suspense>
          ),
        },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default AppRouter;
