import { ERROR_CODE } from '@constants/api';
import { toastListState } from '@store/toast';
import { useMutation } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { generateUniqueId } from '@utils/uniqueId';

import type { ErrorResponseData } from '@api/interceptors';
import { postTrip } from '@api/trip/postTrip';

import { useTokenError } from '@hooks/member/useTokenError';

export const useCreateTripMutation = () => {
  const setToastList = useSetRecoilState(toastListState);

  const { handleTokenError } = useTokenError();

  const newTripMutation = useMutation({
    mutationFn: postTrip,
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
        handleTokenError();

        return;
      }

      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '새로운 여행기록을 생성하지 못했습니다. 잠시 후 다시 시도해주세요.',
        },
      ]);
    },
  });

  return newTripMutation;
};
