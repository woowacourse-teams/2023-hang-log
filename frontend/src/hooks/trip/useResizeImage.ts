import { viewportWidthState } from '@/store/mediaQuery';

import { useMemo } from 'react';

import { useRecoilValue } from 'recoil';

interface useResizeImageParam {
  width?: number;
  height?: number;
}

interface mobileImageSizeType {
  mobileImageSize: { width: number; height: number };
  modalImageSize: { width: number; height: number };
}

const useResizeImage = ({ width = 0, height = 0 }: useResizeImageParam): mobileImageSizeType => {
  const viewportWidth = useRecoilValue(viewportWidthState);

  const mobileImageWidth = useMemo(() => viewportWidth - 48, [viewportWidth]);
  const mobileImageHeight = useMemo(() => (mobileImageWidth / 4.5) * 3, [mobileImageWidth]);

  const modalImageWidth = useMemo(() => width * 2, [width]);
  const modalImageHeight = useMemo(() => height * 2, [height]);

  return {
    mobileImageSize: {
      height: mobileImageHeight,
      width: mobileImageWidth,
    },
    modalImageSize: {
      height: modalImageHeight,
      width: modalImageWidth,
    },
  };
};

export default useResizeImage;
