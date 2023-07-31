import { focusedIdState } from '@store/scrollFocus';
import { useRecoilValue } from 'recoil';

import TripItemMarker from '@components/common/TripItemMarker/TripItemMarker';

interface TripItemMarkerContainerProps {
  map: google.maps.Map;
  places: { id: number; coordinate: { lat: number; lng: number } }[];
}

const TripItemMarkerContainer = ({ map, places }: TripItemMarkerContainerProps) => {
  const selectedItemId = useRecoilValue(focusedIdState);

  return places.map((place) => (
    <TripItemMarker
      map={map}
      id={place.id}
      lat={place.coordinate.lat}
      lng={place.coordinate.lng}
      isSelected={place.id === selectedItemId}
    />
  ));
};

export default TripItemMarkerContainer;
