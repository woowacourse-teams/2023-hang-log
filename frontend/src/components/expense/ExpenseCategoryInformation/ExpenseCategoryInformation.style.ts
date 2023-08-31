import { css } from '@emotion/react';

import { Theme } from 'hang-log-design-system';

import type { ExpenseCategoryNameType } from '@type/expense';

import { EXPENSE_CHART_COLORS } from '@constants/expense';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing3,

  width: '100%',
  marginTop: Theme.spacer.spacing6,
});

export const wrapperStyling = css({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
});

export const getCategoryIconStyling = (categoryName: ExpenseCategoryNameType) => {
  return css({
    width: '16px',
    height: '16px',
    borderRadius: '50%',

    backgroundColor: EXPENSE_CHART_COLORS[categoryName],
  });
};

export const categoryNameStyling = css({
  fontWeight: 'bold',
});
