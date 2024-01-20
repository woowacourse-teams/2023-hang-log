import CloseIcon from '@assets/svg/close-icon.svg';
import { TOAST_CLOSE_ANIMATION_DURATION, TOAST_SHOW_DURATION } from '@constants/index';
import type { ComponentPropsWithoutRef } from 'react';
import { useCallback, useEffect, useRef, useState } from 'react';
import { createPortal } from 'react-dom';

import {
  closeIconStyling,
  contentStyling,
  getToastStyling,
  getVariantStyling,
} from '@components/Toast/Toast.style';

export interface ToastProps extends ComponentPropsWithoutRef<'div'> {
  /**
   * Toast의 비주얼 스타일 - 디폴트, 성공, 에러
   *
   * @default 'default'
   */
  variant?: 'default' | 'success' | 'error';
  /**
   * Toast를 닫을 수 있는지에 대한 여부
   *
   * @default false
   */
  hasCloseButton?: boolean;
  /**
   * Toast를 보여주는 시간
   *
   * @default 2000
   */
  showDuration?: number;
  /** Toast를 닫을 때 실행할 함수 */
  onClose: () => void;
}

const Toast = ({
  variant = 'default',
  hasCloseButton = false,
  showDuration = TOAST_SHOW_DURATION,
  onClose,
  children,
  ...attributes
}: ToastProps) => {
  const [isAdded, setIsAdded] = useState(true);
  const [isVisible, setIsVisible] = useState(true);

  const showAnimationRef = useRef<NodeJS.Timeout>();
  const hideAnimationRef = useRef<NodeJS.Timeout>();

  const handleClose = useCallback(() => {
    hideAnimationRef.current = setTimeout(() => {
      setIsAdded(false);
      onClose?.();
      clearTimeout(showAnimationRef.current);
    }, TOAST_CLOSE_ANIMATION_DURATION);
  }, [onClose]);

  useEffect(() => {
    showAnimationRef.current = setTimeout(() => {
      setIsVisible(false);
      handleClose();
    }, showDuration);

    return () => clearTimeout(hideAnimationRef.current);
  }, [handleClose, showDuration]);

  return (
    isAdded &&
    createPortal(
      <div
        css={[getToastStyling(isVisible), getVariantStyling(variant)]}
        role="alert"
        aria-live="assertive"
        {...attributes}
      >
        <span css={contentStyling}>{children}</span>
        {hasCloseButton && <CloseIcon css={closeIconStyling} onClick={handleClose} />}
      </div>,
      document.getElementById('toast-container') as Element
    )
  );
};

export default Toast;
