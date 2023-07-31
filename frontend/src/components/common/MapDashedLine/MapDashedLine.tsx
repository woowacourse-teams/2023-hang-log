import { Theme } from 'hang-log-design-system';
import { useEffect } from 'react';

interface MapPolylineProps {
  coordinates: { lat: number; lng: number }[];
  map: google.maps.Map;
}

const MapDashedLine = ({ coordinates, map }: MapPolylineProps) => {
  useEffect(() => {
    const line = new google.maps.Polyline({
      path: coordinates,
      strokeOpacity: 0,
      icons: [
        {
          icon: {
            path: 'M 0,-1 0,1',
            strokeOpacity: 1,
            strokeColor: Theme.color.gray600,
            scale: 4,
          },
          offset: '0',
          repeat: '20px',
        },
      ],
      map,
    });

    return () => {
      line.setMap(null);
    };
  }, [coordinates, map]);

  return null;
};

export default MapDashedLine;
