import { useSetRecoilState } from 'recoil';

import { toastListState } from '@store/toast';

import { generateUniqueId } from '@utils/uniqueId';

import type { ToastType } from '@type/toast';

export const useToast = () => {
  const setToastList = useSetRecoilState(toastListState);

  const generateToast = (message: string, variant?: ToastType['variant']) => {
    const newToast = { id: generateUniqueId(), variant: variant ?? 'default', message };

    setToastList((prevToastList) => [...prevToastList, newToast]);
  };

  return { generateToast };
};
