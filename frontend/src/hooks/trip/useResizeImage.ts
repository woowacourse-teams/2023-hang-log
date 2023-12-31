import { useMemo } from 'react';

import { useRecoilValue } from 'recoil';

import { viewportWidthState } from '@store/mediaQuery';

const useResizeImage = () => {
  const viewportWidth = useRecoilValue(viewportWidthState);

  const width = useMemo(() => viewportWidth - 48, [viewportWidth]);
  const height = useMemo(() => (width / 4.5) * 3, [width]);

  return { width, height };
};

export default useResizeImage;
