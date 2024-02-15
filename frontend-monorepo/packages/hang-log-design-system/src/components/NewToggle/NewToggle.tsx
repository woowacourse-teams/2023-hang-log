import type { PropsWithChildren } from 'react';
import { useMemo, createContext } from 'react';
import { useSelect } from '@hooks/useSelect';
import List from '@components/NewToggle/List';
import Item from '@components/NewToggle/Item';

interface ToggleContextType {
  selectKey: number | string;
  handleSelect: (selectedId: number | string) => void;
}

export interface NewToggleProps extends PropsWithChildren {
  initialSelect?: number | string;
}

export const NewToggleContext = createContext<ToggleContextType | null>(null);

const NewToggle = ({ initialSelect = 0, children }: NewToggleProps) => {
  const { selected, handleSelectClick } = useSelect(initialSelect);

  const context = useMemo(
    () => ({
      selectKey: selected,
      handleSelect: handleSelectClick,
    }),
    [handleSelectClick, selected]
  );

  return <NewToggleContext.Provider value={context}>{children}</NewToggleContext.Provider>;
};

NewToggle.List = List;
NewToggle.Item = Item;

export default NewToggle;
