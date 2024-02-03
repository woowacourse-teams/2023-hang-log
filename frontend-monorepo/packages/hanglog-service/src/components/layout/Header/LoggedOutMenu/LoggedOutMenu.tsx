import { useNavigate } from 'react-router-dom';

import { Button } from 'hang-log-design-system';

import { PATH } from '@constants/path';

const LoggedOutMenu = () => {
  const navigate = useNavigate();

  return (
    <Button type="button" variant="primary" size="small" onClick={() => navigate(PATH.LOGIN)}>
      로그인
    </Button>
  );
};

export default LoggedOutMenu;
