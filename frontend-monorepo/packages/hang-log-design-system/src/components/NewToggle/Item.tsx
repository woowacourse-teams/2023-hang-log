import type { ForwardedRef, PropsWithChildren } from 'react';
import { useContext, forwardRef } from 'react';
import { NewToggleContext } from '@components/NewToggle/NewToggle';

export interface ItemProps extends PropsWithChildren {
  toggleKey: number | string;
}

const ToggleItem = ({ children, toggleKey }: ItemProps, ref?: ForwardedRef<HTMLDivElement>) => {
  const context = useContext(NewToggleContext);

  if (!context) throw new Error('NewToggle 컴포넌트가 Wrapping되어있지 않습니다.');

  const { selectKey } = context;

  return (
    <div ref={ref} style={{ display: selectKey === toggleKey ? 'block' : 'none' }}>
      {children}
    </div>
  );
};

export default forwardRef(ToggleItem);
