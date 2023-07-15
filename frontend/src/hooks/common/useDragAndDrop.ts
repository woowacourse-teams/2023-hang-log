import { useCallback, useState } from 'react';

export const useDragAndDrop = <T>(
  /** 드래그가 요소들의 초기 목록 */
  initialItems: T[],
  /** 드래그가 끝난 후 실행될 함수 */
  onPositionChange?: (newItems: T[]) => void
) => {
  /** 현재 드래그 되는 요소의 인덱스 */
  const [draggingIndex, setDraggingIndex] = useState(-1);
  /** 현재 드래그 되는 요소가 놓여질 위치의 인덱스 */
  const [dragOverIndex, setDragOverIndex] = useState(-1);
  const [items, setItems] = useState([...initialItems]);

  const handleItemsUpdate = useCallback((newItems: T[]) => {
    setItems(newItems);
  }, []);

  const handleDragStart = useCallback(
    (index: number) => () => {
      setDraggingIndex(index);
    },
    []
  );

  const handleDragEnter = useCallback(
    (index: number) => () => {
      setDragOverIndex(index);
    },
    []
  );

  const handleDragEnd = useCallback(() => {
    console.log('drop');
    const draggingItem = items[draggingIndex];
    const newItems = [...items];
    newItems.splice(draggingIndex, 1);
    newItems.splice(dragOverIndex, 0, draggingItem);

    setDraggingIndex(-1);
    setDragOverIndex(-1);
    setItems(newItems);
    onPositionChange?.(newItems);
  }, [dragOverIndex, draggingIndex, items]);

  return {
    items,
    handleItemsUpdate,
    handleDragStart,
    handleDragEnter,
    handleDragEnd,
  };
};
