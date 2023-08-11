import { useCallback, useEffect, useRef } from 'react';

import { useSetRecoilState } from 'recoil';

import { mediaQueryMobileState, viewportWidthState } from '@store/mediaQuery';

import { MOBILE_MEDIA_QUERY_SIZE } from '@constants/ui';

export const useMediaQuery = () => {
  const setViewportWidth = useSetRecoilState(viewportWidthState);
  const setIsMobile = useSetRecoilState(mediaQueryMobileState);
  const mediaQueryRef = useRef<MediaQueryList | null>(null);

  const handleWindowResize = useCallback(() => {
    setIsMobile(window.matchMedia(MOBILE_MEDIA_QUERY_SIZE).matches);
    setViewportWidth(window.innerWidth);
  }, [setIsMobile, setViewportWidth]);

  const handleViewportWidthChange = useCallback(() => {
    setViewportWidth(window.innerWidth);
  }, [setViewportWidth]);

  useEffect(() => {
    setIsMobile(window.matchMedia(MOBILE_MEDIA_QUERY_SIZE).matches);
    setViewportWidth(window.innerWidth);
  }, [setIsMobile, setViewportWidth]);

  useEffect(() => {
    const mediaQueryList = window.matchMedia(MOBILE_MEDIA_QUERY_SIZE);
    mediaQueryRef.current = mediaQueryList;

    mediaQueryRef.current.addEventListener('change', handleWindowResize);
    window.addEventListener('resize', handleViewportWidthChange);

    return () => {
      mediaQueryRef.current?.removeEventListener('change', handleWindowResize);
      window.removeEventListener('resize', handleViewportWidthChange);
    };
  }, [handleWindowResize, handleViewportWidthChange]);
};
