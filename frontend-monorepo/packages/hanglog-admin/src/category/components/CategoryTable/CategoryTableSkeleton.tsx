import { Skeleton } from 'hang-log-design-system';

import { tableStyling } from './CategoryTable.style';

interface CategoryTableSkeletonProps {
  length: number;
}

const CategoryTableSkeleton = ({ length }: CategoryTableSkeletonProps) => {
  return (
    <table css={tableStyling}>
      <thead>
        <tr>
          <th>ID</th>
          <th>영문명</th>
          <th>국문명</th>
          <th> </th>
        </tr>
      </thead>
      <tbody>
        {Array.from({ length }, (_, index) => {
          return (
            <tr key={index}>
              <td colSpan={4}>
                <Skeleton />
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

export default CategoryTableSkeleton;
