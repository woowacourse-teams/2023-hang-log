import { useState } from 'react';

export const useDraggedItem = (onDragEnd?: () => void) => {
  const [isDragging, setIsDragging] = useState(false);

  const handleDrag = () => {
    setIsDragging(true);
  };

  const handleDragEnd = () => {
    setIsDragging(false);
    onDragEnd?.();
  };

  return { isDragging, handleDrag, handleDragEnd };
};
