import type { CityData } from '@type/city';

import CityEditButton from '../CityEditButton/CityEditButton';
import { tableStyling } from './CityTable.style';

interface CityTableProps {
  cities: CityData[];
}

const CityTable = ({ cities }: CityTableProps) => {
  return (
    <table css={tableStyling}>
      <thead>
        <tr>
          <th>ID</th>
          <th>도시</th>
          <th>나라</th>
          <th>위도</th>
          <th>경도</th>
          <th> </th>
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
            <td>
              <CityEditButton {...city} />
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default CityTable;
