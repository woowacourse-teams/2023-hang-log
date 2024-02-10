import { Select } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { memo } from 'react';

import { AdminMemberFormData, SelectableAdminType } from '@type/adminMember';

interface AdminTypeSelectProps {
  value: string;
  updateInputValue: <K extends keyof AdminMemberFormData>(
    key: K,
    value: AdminMemberFormData[K]
  ) => void;
}

const AdminTypeSelect = ({ value, updateInputValue }: AdminTypeSelectProps) => {
  const handleAdminTypeChange = (event: ChangeEvent<HTMLSelectElement>) => {
    const newAdminTypeValue = event.target.value;
    updateInputValue('adminType', newAdminTypeValue);
  };

  return (
    <Select
      label="관리자 등급"
      name="adminType"
      value={value}
      aria-label="관리자 등급"
      onChange={handleAdminTypeChange}
    >
      {SelectableAdminType.map((key) => {
        return (
          <option key={key} value={key}>
            {key}
          </option>
        );
      })}
    </Select>
  );
};

export default memo(AdminTypeSelect);
