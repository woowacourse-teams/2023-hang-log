import type { RefObject } from 'react';

export const useAutoScroll = (
  listRef: RefObject<HTMLElement>,
  focusItemRef: RefObject<HTMLLIElement | HTMLDivElement>
) => {
  const scrollToFocusedItem = () => {
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
  };

  const scrollToCenter = () => {
    const list = listRef.current;
    const focusedItem = focusItemRef.current;

    if (list && focusedItem) {
      focusedItem.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
  };

  return { scrollToFocusedItem, scrollToCenter };
};
