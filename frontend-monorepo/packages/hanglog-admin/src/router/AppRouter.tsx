import App from '@/App';

import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage';
import AdminMainPage from '@/pages/AdminMainPage/AdminMainPage';
import AdminMemberPage from '@/pages/AdminMemberPage/AdminMemberPage';
import CityPage from '@/pages/CityPage/CityPage';
import CategoryPage from '@/pages/CategoryPage/CategoryPage';
import CurrencyPage from '@/pages/CurrencyPage/CurrencyPage';

import CityPageSkeleton from '@/pages/CityPage/CityPageSkeleton';
import CategoryPageSkeleton from '@/pages/CategoryPage/CategoryPageSkeleton';
import CurrencyPageSkeleton from '@/pages/CurrencyPage/CurrencyPageSkeleton';

import { PATH } from '@constants/path';
import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import AdminMemberPageSkeleton from '@/pages/AdminMemberPage/AdminMemberPageSkeleton';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
    errorElement: <NotFoundPage />,
    children: [
      {
        path: '',
        element: <div></div>,
      },
      {
        path: PATH.HOME,
        element: (
          <Suspense>
            <AdminMainPage />
          </Suspense>
        ),
      },
      {
        path: PATH.ADMIN_MEMBER,
        element: (
          <Suspense fallback={<AdminMemberPageSkeleton />}>
            <AdminMemberPage />
          </Suspense>
        ),
      },
      {
        path: PATH.CITY,
        element: (
          <Suspense fallback={<CityPageSkeleton />}>
            <CityPage />
          </Suspense>
        ),
      },
      {
        path: PATH.CATEGORY,
        element: (
          <Suspense fallback={<CategoryPageSkeleton />}>
            <CategoryPage />
          </Suspense>
        ),
      },
      {
        path: PATH.CURRENCY,
        element: (
          <Suspense fallback={<CurrencyPageSkeleton />}>
            <CurrencyPage />
          </Suspense>
        ),
      },
    ],
  },
]);

const AppRouter = () => {
  return <RouterProvider router={router} />;
};

export default AppRouter;
