import { useCallback, useEffect, useRef } from 'react';

import { useSetRecoilState } from 'recoil';

import { MOBILE_MEDIA_QUERY_SIZE } from '@constants/ui';

import { mediaQueryMobileState, viewportHeightState, viewportWidthState } from '@store/mediaQuery';

export const useMediaQuery = () => {
  const setViewportWidth = useSetRecoilState(viewportWidthState);
  const setViewportHeight = useSetRecoilState(viewportHeightState);
  const setIsMobile = useSetRecoilState(mediaQueryMobileState);
  const mediaQueryRef = useRef<MediaQueryList | null>(null);

  const handleWindowResize = useCallback(() => {
    setIsMobile(window.matchMedia(MOBILE_MEDIA_QUERY_SIZE).matches);
    setViewportWidth(window.innerWidth);
    setViewportHeight(window.innerHeight);
  }, [setIsMobile, setViewportWidth, setViewportHeight]);

  const handleViewportDimensionChange = useCallback(() => {
    setViewportWidth(window.innerWidth);
    setViewportHeight(window.innerHeight);
  }, [setViewportWidth, setViewportHeight]);

  useEffect(() => {
    setIsMobile(window.matchMedia(MOBILE_MEDIA_QUERY_SIZE).matches);
    setViewportWidth(window.innerWidth);
    setViewportHeight(window.innerHeight);
  }, [setIsMobile, setViewportWidth, setViewportHeight]);

  useEffect(() => {
    const mediaQueryList = window.matchMedia(MOBILE_MEDIA_QUERY_SIZE);
    mediaQueryRef.current = mediaQueryList;

    mediaQueryRef.current.addEventListener('change', handleWindowResize);
    window.addEventListener('resize', handleViewportDimensionChange);

    return () => {
      mediaQueryRef.current?.removeEventListener('change', handleWindowResize);
      window.removeEventListener('resize', handleViewportDimensionChange);
    };
  }, [handleWindowResize, handleViewportDimensionChange]);
};
