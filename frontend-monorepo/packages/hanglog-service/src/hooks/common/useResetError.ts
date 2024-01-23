import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

import { HTTPError } from '@api/HTTPError';

import { HTTP_STATUS_CODE } from '@constants/api';
import { PATH } from '@constants/path';

export const useResetError = () => {
  const navigate = useNavigate();

  const handleErrorReset = useCallback(
    (error: Error | HTTPError) => {
      if (error instanceof Error && !(error instanceof HTTPError)) {
        navigate(PATH.ROOT);

        return;
      }

      if (error.statusCode >= HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR) {
        navigate(PATH.RELOAD);
      } else {
        navigate(PATH.ROOT);
      }
    },
    [navigate]
  );

  return { handleErrorReset };
};
