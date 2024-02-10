import CategoryEditButton from '../CategoryEditButton/CategoryEditButton';

import type { CategoryData } from '@type/category';

import { tableStyling } from './CategoryTable.style';

interface CategoryTableProps {
  categories: CategoryData[];
}

const CategoryTable = ({ categories }: CategoryTableProps) => {
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
        {categories.map((category) => (
          <tr key={category.id}>
            <td>{category.id}</td>
            <td>{category.engName}</td>
            <td>{category.korName}</td>
            <td>
              <CategoryEditButton {...category} />
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default CategoryTable;
