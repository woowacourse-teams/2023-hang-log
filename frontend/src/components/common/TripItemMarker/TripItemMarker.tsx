import { useEffect } from 'react';

interface TripItemMarkerProps {
  map: google.maps.Map;
  id: number;
  lat: number;
  lng: number;
}

const TripItemMarker = ({ map, id, lat, lng }: TripItemMarkerProps) => {
  useEffect(() => {
    const marker = new google.maps.marker.AdvancedMarkerElement({
      position: { lat, lng },
      map,
      title: '몰라요',
    });
  }, [lat, lng]);

  return <></>;
};

export default TripItemMarker;
