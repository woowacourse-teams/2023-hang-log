import { Outlet } from 'react-router-dom';

import Header from '@components/layout/Header/Header';
import Footer from '@components/layout/Footer/Footer';
import ToastContainer from '@components/common/ToastContainer/ToastContainer';

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
