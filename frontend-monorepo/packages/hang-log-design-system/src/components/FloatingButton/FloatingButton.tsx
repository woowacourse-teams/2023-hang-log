import AddIcon from '@assets/svg/add-icon.svg';
import type { Size } from '@type/index';
import type { ComponentPropsWithoutRef } from 'react';

import { getVariantStyling } from '@components/Button/Button.style';
import {
  floatingButtonStyling,
  getIconSizeStyling,
  getIconVariantStyling,
  getSizeStyling,
} from '@components/FloatingButton/FloatingButton.style';

export interface FloatingButtonProps extends ComponentPropsWithoutRef<'button'> {
  /**
   * FloatingButton의 시이즈
   *
   * @default 'medium'
   */
  size?: Extract<Size, 'medium' | 'small'>;
  /**
   * FloatingButton의 색상
   *
   * @default 'primary'
   */
  variant?: 'primary' | 'default';
}

const FloatingButton = ({
  size = 'medium',
  variant = 'primary',
  ...attributes
}: FloatingButtonProps) => {
  return (
    <button
      type="button"
      css={[floatingButtonStyling, getSizeStyling(size), getVariantStyling(variant)]}
      {...attributes}
    >
      <AddIcon
        aria-label="추가 버튼"
        css={[getIconSizeStyling(size), getIconVariantStyling(variant)]}
      />
    </button>
  );
};

export default FloatingButton;
