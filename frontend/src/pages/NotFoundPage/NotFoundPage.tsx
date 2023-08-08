import { PATH } from '@constants/path';
import { useNavigate } from 'react-router-dom';

import { useMediaQuery } from '@hooks/common/useMediaQuery';

import Error from '@components/common/Error/Error';

const NotFoundPage = () => {
  const navigate = useNavigate();

  useMediaQuery();

  return <Error resetError={() => navigate(PATH.ROOT)} />;
};

export default NotFoundPage;
