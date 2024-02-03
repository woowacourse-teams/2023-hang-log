import type { AdminMemberData } from '@/types/adminMember';

import { tableStyling } from './AdminMemberTable.style';

import PasswordEditButton from '../PasswordEditButton/PasswordEditButton';

interface AdminmemberTableProps {
  adminMembers: AdminMemberData[];
}

const AdminMemberTable = ({ adminMembers }: AdminmemberTableProps) => {
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
        {adminMembers.map((adminMember) => (
          <tr key={adminMember.id}>
            <td>{adminMember.id}</td>
            <td>{adminMember.username}</td>
            <td>{adminMember.adminType}</td>
            <td>
              <PasswordEditButton id={adminMember.id} />
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default AdminMemberTable;
