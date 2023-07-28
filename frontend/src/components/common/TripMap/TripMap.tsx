import { useEffect, useRef, useState } from 'react';

interface TripMapProps {
  places: { id: number; coordinate: { lat: number; lng: number } }[];
}

const TripMap = ({ places }: TripMapProps) => {
  // 구글 지도 만들어야 함 이거 상태로 저장해야 함 왜냐하면 마커에서도 사용해야함 라인 그릴떄도
  const [map, setMap] = useState<google.maps.Map | null>(null);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    // center 좌표 뽑기
    const bounds = new google.maps.LatLngBounds();
    places.forEach((place) =>
      bounds.extend(new google.maps.LatLng(place.coordinate.lat, place.coordinate.lng))
    );
    const center = bounds.getCenter();

    const initialMap = new google.maps.Map(wrapperRef.current!, {
      center,
      disableDefaultUI: true,
      mapId: '30895ea02d4d1639',
    });

    initialMap.fitBounds(bounds);

    setMap(initialMap);
  }, []);

  return <div id="map" ref={wrapperRef} css={{ minHeight: '100vh' }} />;
};

export default TripMap;
