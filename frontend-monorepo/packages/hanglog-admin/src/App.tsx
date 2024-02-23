import { Outlet } from 'react-router-dom';

import ToastContainer from '@components/common/ToastContainer/ToastContainer';
import Footer from '@components/layout/Footer/Footer';
import Header from '@components/layout/Header/Header';

const App = () => {
  return (
    <div>
      <Header />
      <main>
        <Outlet />
      </main>
      <Footer />
      <ToastContainer />
    </div>
  );
};

export default App;
