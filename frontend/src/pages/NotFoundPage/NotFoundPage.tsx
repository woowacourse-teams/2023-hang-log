import { PATH } from '@constants/path';
import { useNavigate } from 'react-router-dom';

import Error from '@components/common/Error/Error';

const NotFoundPage = () => {
  const navigate = useNavigate();

  return <Error resetError={() => navigate(PATH.ROOT)} />;
};

export default NotFoundPage;
