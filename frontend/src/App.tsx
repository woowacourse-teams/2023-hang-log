import { Outlet } from 'react-router-dom';

import Error from '@components/common/Error/Error';
import ErrorBoundary from '@components/common/ErrorBoundary/ErrorBoundary';
import LogIn from '@components/common/LogIn/LogIn';
import ScrollTop from '@components/common/ScrollTop/ScrollTop';
import ToastContainer from '@components/common/ToastContainer/ToastContainer';
import Footer from '@components/layout/Footer/Footer';
import Header from '@components/layout/Header/Header';

import { useMediaQuery } from '@hooks/common/useMediaQuery';
import { useResetError } from '@hooks/common/useResetError';

const App = () => {
  const { handleErrorReset } = useResetError();
  useMediaQuery();

  return (
    <ErrorBoundary Fallback={Error} onReset={handleErrorReset}>
      <ScrollTop />
      <LogIn>
        <Header />
        <main>
          <Outlet />
        </main>
        <Footer />
      </LogIn>
      <ToastContainer />
    </ErrorBoundary>
  );
};

export default App;
