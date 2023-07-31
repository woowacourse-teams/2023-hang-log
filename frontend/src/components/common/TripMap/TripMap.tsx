import { MAP_INITIAL_ZOOM_SIZE, MAP_MAX_ZOOM_SIZE } from '@constants/map';
import type { TripPlaceType } from '@type/trip';
import { useEffect, useRef, useState } from 'react';

import MapDashedLine from '@components/common/MapDashedLine/MapDashedLine';
import TripItemMarker from '@components/common/TripItemMarker/TripItemMarker';

interface TripMapProps {
  centerLat: number;
  centerLng: number;
  places: TripPlaceType[];
}

const TripMap = ({ centerLat, centerLng, places }: TripMapProps) => {
  const [map, setMap] = useState<google.maps.Map | null>(null);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  const coordinates = places.map((place) => ({
    lat: place.coordinate.lat,
    lng: place.coordinate.lng,
  }));

  useEffect(() => {
    if (wrapperRef.current) {
      const initialMap = new google.maps.Map(wrapperRef.current, {
        center: { lat: centerLat, lng: centerLng },
        zoom: MAP_INITIAL_ZOOM_SIZE,
        maxZoom: MAP_MAX_ZOOM_SIZE,
        disableDefaultUI: true,
        mapId: process.env.GOOGLE_MAP_ID,
      });

      setMap(initialMap);
    }
  }, [centerLat, centerLng]);

  useEffect(() => {
    if (places.length > 0) {
      const bounds = new google.maps.LatLngBounds();
      places.forEach((place) =>
        bounds.extend(new google.maps.LatLng(place.coordinate.lat, place.coordinate.lng))
      );
      const center = bounds.getCenter();

      map?.panTo(center);
      map?.fitBounds(bounds);
    } else {
      map?.panTo({ lat: centerLat, lng: centerLng });
      map?.setZoom(MAP_INITIAL_ZOOM_SIZE);
    }
  }, [places, map, centerLat, centerLng]);

  return (
    <>
      <div id="map" ref={wrapperRef} css={{ height: 'calc(100vh - 81px)' }} />
      {map && (
        <>
          {places.map((place, index) => (
            <TripItemMarker
              map={map}
              id={place.id}
              lat={place.coordinate.lat}
              lng={place.coordinate.lng}
              isSelected={index === 0}
            />
          ))}
          <MapDashedLine map={map} coordinates={coordinates} />
        </>
      )}
    </>
  );
};

export default TripMap;
