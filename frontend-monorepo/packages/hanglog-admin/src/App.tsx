import { Outlet } from 'react-router-dom';

const App = () => {
  return (
    <div>
      <Outlet />
      <p>App Component</p>
    </div>
  );
};

export default App;
