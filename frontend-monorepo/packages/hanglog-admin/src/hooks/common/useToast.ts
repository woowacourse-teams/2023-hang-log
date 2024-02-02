import { useCallback } from 'react';

import { useSetRecoilState } from 'recoil';

import { toastListState } from '@store/toast';

import { generateUniqueId } from '@/utils/uniqueId';

import type { ToastType } from '@type/toast';

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
