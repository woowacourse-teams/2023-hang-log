import { useCallback } from 'react';
import type { RefObject } from 'react';

export const useAutoScroll = (
  listRef: RefObject<HTMLElement>,
  focusItemRef: RefObject<HTMLElement>
) => {
  const scrollToFocusedItem = useCallback(() => {
    const list = listRef.current;
    const focusedItem = focusItemRef.current;

    if (list && focusedItem) {
      const listRect = list.getBoundingClientRect();
      const focusedItemRect = focusedItem.getBoundingClientRect();

      const scrollOffset =
        focusedItemRect.top - listRect.top - listRect.height / 2 + focusedItemRect.height / 2;

      list.scrollTo({
        top: list.scrollTop + scrollOffset,
        behavior: 'smooth',
      });
    }
  }, [focusItemRef, listRef]);

  const scrollToCenter = useCallback(() => {
    const list = listRef.current;
    const focusedItem = focusItemRef.current;

    if (list && focusedItem) {
      focusedItem.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
  }, [focusItemRef, listRef]);

  return { scrollToFocusedItem, scrollToCenter };
};
