import { Skeleton } from 'hang-log-design-system';

import { tableStyling } from './CityTable.style';

interface CityTableSkeletonProps {
  length: number;
}

const CityTableSkeleton = ({ length }: CityTableSkeletonProps) => {
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
        {Array.from({ length }, (_, index) => {
          return (
            <tr key={index}>
              <td colSpan={6}>
                <Skeleton />
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

export default CityTableSkeleton;
