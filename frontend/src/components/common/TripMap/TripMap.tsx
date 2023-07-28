import { Theme } from 'hang-log-design-system';
import { useEffect, useRef, useState } from 'react';

import TripItemMarker from '../TripItemMarker/TripItemMarker';

interface TripMapProps {
  places: { id: number; coordinate: { lat: number; lng: number } }[];
}

const TripMap = ({ places }: TripMapProps) => {
  const [map, setMap] = useState<google.maps.Map | null>(null);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const bounds = new google.maps.LatLngBounds();
    places.forEach((place) =>
      bounds.extend(new google.maps.LatLng(place.coordinate.lat, place.coordinate.lng))
    );
    const center = bounds.getCenter();

    const initialMap = new google.maps.Map(wrapperRef.current!, {
      center,
      disableDefaultUI: true,
      mapId: process.env.GOOGLE_MAP_ID,
    });

    initialMap.fitBounds(bounds);

    const lineSymbol = {
      path: 'M 0,-1 0,1',
      strokeOpacity: 1,
      scale: 4,
    };

    const coordinates = places.map((place) => ({
      lat: place.coordinate.lat,
      lng: place.coordinate.lng,
    }));

    new google.maps.Polyline({
      path: coordinates,
      strokeOpacity: 0,
      icons: [
        {
          icon: lineSymbol,
          offset: '0',
          repeat: '20px',
        },
      ],
      map: initialMap,
    });

    setMap(initialMap);
  }, [places]);

  return (
    <>
      <div id="map" ref={wrapperRef} css={{ minHeight: '100vh' }} />
      {map && (
        <>
          {places.map((place) => (
            <TripItemMarker
              map={map}
              id={place.id}
              lat={place.coordinate.lat}
              lng={place.coordinate.lng}
            />
          ))}
        </>
      )}
    </>
  );
};

export default TripMap;
