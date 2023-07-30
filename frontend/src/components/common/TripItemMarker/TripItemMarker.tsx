import { useEffect, useState } from 'react';

interface TripItemMarkerProps {
  map: google.maps.Map;
  id: number;
  lat: number;
  lng: number;
  isSelected: boolean;
}

const TripItemMarker = ({ map, id, lat, lng, isSelected }: TripItemMarkerProps) => {
  const [marker, setMarker] = useState<google.maps.marker.AdvancedMarkerElement | null>(null);

  useEffect(() => {
    const marker = new google.maps.marker.AdvancedMarkerElement({
      position: { lat, lng },
      map,
    });

    setMarker(marker);

    return () => {
      marker.map = null;
    };
  }, [id, lat, lng, map]);

  useEffect(() => {
    if (marker) {
      const pin = isSelected
        ? new google.maps.marker.PinElement({
            glyphColor: '#D83AA2',
            background: '#EA73C1',
            borderColor: '#D83AA2',
          })
        : new google.maps.marker.PinElement({
            glyphColor: '#0083BB',
            background: '#26A8E0',
            borderColor: '#0083BB',
          });

      marker.content = pin.element;
    }
  }, [isSelected, marker]);

  return null;
};

export default TripItemMarker;
