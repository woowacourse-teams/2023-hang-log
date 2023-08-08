import { Theme } from 'hang-log-design-system';

export const EXPENSE_LIST_FILTERS = {
  DAY_LOG: '날짜',
  CATEGORY: '카테고리',
} as const;

export const EXPENSE_CATEGORY_NAMES = ['음식', '문화', '쇼핑', '숙박', '교통', '기타'] as const;

export const EXPENSE_CHART_COLORS = {
  음식: '#FFA927',
  문화: '#CD5CF4',
  쇼핑: '#FE86D5',
  숙박: '#2FC56E',
  교통: Theme.color.blue500,
  기타: '#FFD600',
} as const;
