import { useNavigate } from 'react-router-dom';

import Error from '@components/common/Error/Error';

import { useMediaQuery } from '@hooks/common/useMediaQuery';

import { PATH } from '@constants/path';

const NotFoundPage = () => {
  const navigate = useNavigate();

  useMediaQuery();

  return <Error resetError={() => navigate(PATH.ROOT)} />;
};

export default NotFoundPage;
