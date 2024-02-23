import { Skeleton } from 'hang-log-design-system';

import { tableStyling } from './CurrencyTable.style';

interface CurrencyTableSkeletonProps {
  length: number;
}

const CurrencyTableSkeleton = ({ length }: CurrencyTableSkeletonProps) => {
  return (
    <table css={tableStyling}>
      <thead>
        <tr>
          <th>ID</th>
          <th>날짜</th>
          <th>USD</th>
          <th>EUR</th>
          <th>GBP</th>
          <th>JPY(100)</th>
          <th>CNY</th>
          <th>CHF</th>
          <th>SGD</th>
          <th>THB</th>
          <th>HKD</th>
          <th> </th>
        </tr>
      </thead>
      <tbody>
        {Array.from({ length }, (_, index) => {
          return (
            <tr key={index}>
              <td colSpan={12}>
                <Skeleton />
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

export default CurrencyTableSkeleton;
