import { lazy } from 'react';

export const ExpensePage = lazy(
  () => import(/* webpackChunkName: "ExpensePage" */ '@pages/ExpensePage/ExpensePage')
);

export const CommunityPage = lazy(
  () => import(/* webpackChunkName: "IntroPage" */ '@/pages/CommunityPage/CommunityPage')
);

export const LogInPage = lazy(
  () => import(/* webpackChunkName: "LogInPage" */ '@pages/LogInPage/LogInPage')
);

export const MyPage = lazy(() => import(/* webpackChunkName: "MyPage" */ '@pages/MyPage/MyPage'));

export const SharedTripPage = lazy(
  () => import(/* webpackChunkName: "SharedTripPage" */ '@/pages/SharedPage/SharedTripPage')
);

export const TripCreatePage = lazy(
  () => import(/* webpackChunkName: "TripCreatePage" */ '@pages/TripCreatePage/TripCreatePage')
);

export const TripEditPage = lazy(
  () => import(/* webpackChunkName: "TripEditPage" */ '@pages/TripEditPage/TripEditPage')
);

export const TripPage = lazy(
  () => import(/* webpackChunkName: "TripPage" */ '@pages/TripPage/TripPage')
);

export const TripMobilePage = lazy(
  () => import(/* webpackChunkName: "TripMobilePage" */ '@pages/TripPage/TripMobilePage')
);

export const TripsPage = lazy(
  () => import(/* webpackChunkName: "TripsPage" */ '@/pages/MyTripsPage/MyTripsPage')
);

export const CommunityTripPage = lazy(
  () =>
    import(
      /* webpackChunkName: "CommunityTripPage" */ '@/pages/CommunityTripPage/CommunityTripPage'
    )
);
