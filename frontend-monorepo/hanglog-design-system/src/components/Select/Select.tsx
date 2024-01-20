import type { Size } from '@type/index';
import type { ComponentPropsWithRef, ForwardedRef } from 'react';
import { forwardRef } from 'react';

import Label from '@components/Label/Label';
import {
  getSelectStyling,
  getSelectWrapperStyling,
  getSizeStyling,
  selectContainerStyling,
} from '@components/Select/Select.style';
import SupportingText from '@components/SupportingText/SupportingText';

export interface SelectProps extends Omit<ComponentPropsWithRef<'select'>, 'size'> {
  /** Select의 라벨 텍스트 */
  label?: string;
  /**
   * Select의 시이즈
   *
   * @default 'medium'
   */
  size?: Extract<Size, 'small' | 'medium' | 'large'>;
  /**
   * Select 인풋의 에러 여부
   *
   * @default false
   */
  isError?: boolean;
  /** Select에서 선택할 수 있는 JSX option 요소들 */
  children: JSX.Element | JSX.Element[];
  /** Select의 부가 정보 텍스트 */
  supportingText?: string;
}

const Select = (
  { label, size = 'medium', isError = false, children, supportingText, ...attributes }: SelectProps,
  ref: ForwardedRef<HTMLSelectElement>
) => {
  return (
    <div css={selectContainerStyling}>
      {label && (
        <Label id={attributes.id} required={attributes.required}>
          {label}
        </Label>
      )}
      <div css={getSelectWrapperStyling(isError)}>
        <select ref={ref} css={[getSelectStyling(isError), getSizeStyling(size)]} {...attributes}>
          {children}
        </select>
      </div>
      {supportingText && <SupportingText isError={isError}>{supportingText}</SupportingText>}
    </div>
  );
};

export default forwardRef(Select);
