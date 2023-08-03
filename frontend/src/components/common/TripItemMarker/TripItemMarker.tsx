import PinIcon from '@assets/svg/pin-icon.svg';
import SelectedPinIcon from '@assets/svg/selected-pin-icon.svg';
import { clickedMarkerState } from '@store/scrollFocus';
import { Flex, Text } from 'hang-log-design-system';
import { useEffect, useRef } from 'react';
import type { Root } from 'react-dom/client';
import { createRoot } from 'react-dom/client';
import { useSetRecoilState } from 'recoil';

import {
  getLabelStyling,
  getMarkerContainerStyling,
} from '@components/common/TripItemMarker/TripItemMarker.style';

interface TripItemMarkerProps {
  map: google.maps.Map;
  id: number;
  name: string;
  lat: number;
  lng: number;
  isSelected: boolean;
  isZoomedOut: boolean;
}
const TripItemMarker = ({
  map,
  id,
  name,
  lat,
  lng,
  isSelected,
  isZoomedOut,
}: TripItemMarkerProps) => {
  const setClickedMarkerId = useSetRecoilState(clickedMarkerState);

  const markerRef = useRef<google.maps.marker.AdvancedMarkerElement | null>(null);
  const rootRef = useRef<Root | null>(null);

  useEffect(() => {
    if (!rootRef.current) {
      const container = document.createElement('div');
      rootRef.current = createRoot(container);

      markerRef.current = new google.maps.marker.AdvancedMarkerElement({
        position: { lat, lng },
        map,
        content: container,
      });
    }

    return () => {
      if (markerRef.current) markerRef.current.map = null;
    };
  }, [id, lat, lng, map]);

  useEffect(() => {
    if (rootRef.current && markerRef.current) {
      rootRef.current.render(
        <Flex
          styles={{ direction: 'column', align: 'center', gap: '2px' }}
          css={getMarkerContainerStyling(isZoomedOut)}
        >
          {isSelected ? <SelectedPinIcon /> : <PinIcon />}
          {isZoomedOut ? (
            <Text data-text={name} css={getLabelStyling(isSelected)}>
              {name}
            </Text>
          ) : null}
        </Flex>
      );

      markerRef.current.position = { lat, lng };
      markerRef.current.map = map;

      markerRef.current.addListener('click', () => {
        setClickedMarkerId(id);
      });
    }
  }, [isSelected, isZoomedOut, lat, lng, map, name]);

  return null;
};

export default TripItemMarker;
