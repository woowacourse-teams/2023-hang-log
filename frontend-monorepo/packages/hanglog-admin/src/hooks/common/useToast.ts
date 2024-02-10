import { useCallback } from 'react';
import { useSetRecoilState } from 'recoil';

import type { ToastType } from '@type/toast';

import { generateUniqueId } from '@utils/uniqueId';

import { toastListState } from '@store/toast';

export const useToast = () => {
  const setToastList = useSetRecoilState(toastListState);

  const createToast = useCallback(
    (message: string, variant: ToastType['variant'] = 'error') => {
      const newToast = { id: generateUniqueId(), variant, message };

      setToastList((prevToastList) => [...prevToastList, newToast]);
    },
    [setToastList]
  );

  return { createToast };
};
