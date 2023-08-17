import { useSetRecoilState } from 'recoil';

import { toastListState } from '@store/toast';

import { generateUniqueId } from '@utils/uniqueId';

export const useTripShare = (sharedUrl: string | null) => {
  const setToastList = useSetRecoilState(toastListState);

  const handleCopyButtonClick = async () => {
    if (!sharedUrl) return;

    try {
      await navigator.clipboard.writeText(sharedUrl);

      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'default',
          message: '공유 링크가 클립보드에 복사되었습니다.',
        },
      ]);
    } catch {
      setToastList((prevToastList) => [
        ...prevToastList,
        {
          id: generateUniqueId(),
          variant: 'error',
          message: '복사에 실패했습니다. 브라우저 상태를 확인 후 다시 시도해주세요.',
        },
      ]);
    }
  };

  return { handleCopyButtonClick };
};
