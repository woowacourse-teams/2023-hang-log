import type { PropsWithChildren } from 'react';
import { useCallback, useState, useMemo, createContext } from 'react';
import List from '@components/NewToggle/List';
import Item from '@components/NewToggle/Item';
import { flushSync } from 'react-dom';

interface ToggleContextType {
  selectKey: number | string;
  handleSelect: (selectedId: number | string) => void;
}

export interface NewToggleProps extends PropsWithChildren {
  initialSelect?: number | string;
  additinalFunc?: (key: number | string) => void;
}

export const NewToggleContext = createContext<ToggleContextType | null>(null);

const NewToggle = ({ initialSelect = 0, additinalFunc, children }: NewToggleProps) => {
  const [selected, setSelected] = useState<number | string>(initialSelect);

  const handleSelect = useCallback(
    (select: number | string) => {
      flushSync(() => setSelected(select));
      if (additinalFunc) additinalFunc(select);
    },
    [additinalFunc]
  );

  const context = useMemo(
    () => ({
      selectKey: selected,
      handleSelect,
    }),
    [handleSelect, selected]
  );

  return <NewToggleContext.Provider value={context}>{children}</NewToggleContext.Provider>;
};

NewToggle.List = List;
NewToggle.Item = Item;

export default NewToggle;
