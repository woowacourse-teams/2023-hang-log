import { Skeleton } from 'hang-log-design-system';

import { tableStyling } from './AdminMemberTable.style';

interface AdminMemberTableSkeletonProps {
  length: number;
}

const AdminMemberTableSkeleton = ({ length }: AdminMemberTableSkeletonProps) => {
  return (
    <table css={tableStyling}>
      <thead>
        <tr>
          <th>ID</th>
          <th>계정명</th>
          <th>관리자 등급</th>
          <th>비밀번호 수정</th>
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

export default AdminMemberTableSkeleton;
