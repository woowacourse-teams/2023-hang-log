import React from 'react';

import { CityData } from '@/types/city';

interface CityTableProps {
  cities: CityData[];
}

const CityTable = ({ cities }: CityTableProps) => {
  if (!Array.isArray(cities)) {
    return <p>{JSON.stringify(cities)}</p>;
  }

  return (
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>도시</th>
          <th>나라</th>
          <th>위도</th>
          <th>경도</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {cities.map((city) => (
          <tr key={city.id}>
            <td>{city.id}</td>
            <td>{city.name}</td>
            <td>{city.country}</td>
            <td>{city.latitude}</td>
            <td>{city.longitude}</td>
            <td></td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default CityTable;
