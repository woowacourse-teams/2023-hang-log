import { ACCESS_TOKEN_KEY } from '@constants/api';
import { isLoggedInState } from '@store/auth';
import { useLayoutEffect } from 'react';
import { Outlet } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { useMediaQuery } from '@hooks/common/useMediaQuery';
import { useResetError } from '@hooks/common/useResetError';

import Error from '@components/common/Error/Error';
import ErrorBoundary from '@components/common/ErrorBoundary/ErrorBoundary';
import ScrollTop from '@components/common/ScrollTop/ScrollTop';
import ToastContainer from '@components/common/ToastContainer/ToastContainer';
import Footer from '@components/layout/Footer/Footer';
import Header from '@components/layout/Header/Header';

const App = () => {
  const { handleErrorReset } = useResetError();
  useMediaQuery();

  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  useLayoutEffect(() => {
    if (localStorage.getItem(ACCESS_TOKEN_KEY)) {
      setIsLoggedIn(true);
    }
  }, [setIsLoggedIn]);

  return (
    <ErrorBoundary Fallback={Error} onReset={handleErrorReset}>
      <ScrollTop />
      <Header />
      <main>
        <Outlet />
      </main>
      <Footer />
      <ToastContainer />
    </ErrorBoundary>
  );
};

export default App;
