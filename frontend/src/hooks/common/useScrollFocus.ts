import { focusedIdState } from '@/store/scrollFocus';
import { useCallback, useEffect, useState } from 'react';
import { useSetRecoilState } from 'recoil';

const initialOption = {
  root: null,
  rootMargin: '-56% 0px -44% 0px',
  threshold: 0,
};

export const useScrollFocus = (option = initialOption) => {
  const setFocusedId = useSetRecoilState(focusedIdState);
  const [observer, setObserver] = useState<IntersectionObserver | null>(null);

  const handleIntersection = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          setFocusedId(Number(entry.target.getAttribute('data-id')));
        }
      });
    },
    [setFocusedId]
  );

  useEffect(() => {
    const observer = new IntersectionObserver(handleIntersection, option);

    setObserver(observer);

    return () => {
      observer.disconnect();
    };
  }, [handleIntersection, option]);

  return { observer };
};
