import { useCallback, useState } from 'react';

export const useSelect = (initialSelectedId: number | string) => {
  const [selected, setSelected] = useState(initialSelectedId);

  const handleSelectClick = useCallback((selectedId: number | string) => {
    setSelected(selectedId);
  }, []);

  return { selected, handleSelectClick };
};
