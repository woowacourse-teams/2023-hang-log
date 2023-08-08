import { MAP_ZOOM_OUT_LABEL_LIMIT } from '@constants/map';
import { focusedIdState } from '@store/scrollFocus';
import type { TripPlaceType } from '@type/trip';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import TripItemMarker from '@components/common/TripItemMarker/TripItemMarker';

interface TripItemMarkerContainerProps {
  map: google.maps.Map;
  places: TripPlaceType[];
}

const TripItemMarkerContainer = ({ map, places }: TripItemMarkerContainerProps) => {
  const selectedItemId = useRecoilValue(focusedIdState);
  const [isZoomedOut, setIsZoomedOut] = useState(false);

  useEffect(() => {
    map.addListener('zoom_changed', () => {
      const zoom = map.getZoom();

      const isZoomedOut = Number(zoom) > MAP_ZOOM_OUT_LABEL_LIMIT;

      setIsZoomedOut(isZoomedOut);
    });
  });

  return places.map((place) => (
    <TripItemMarker
      map={map}
      isZoomedOut={isZoomedOut}
      id={place.id}
      name={place.name}
      lat={place.coordinate.lat}
      lng={place.coordinate.lng}
      isSelected={place.id === selectedItemId}
    />
  ));
};

export default TripItemMarkerContainer;
