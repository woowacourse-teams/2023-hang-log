import HalfEmptyLeftStar from '@assets/svg/half-empty-left-star.svg';
import HalfEmptyRightStar from '@assets/svg/half-empty-right-star.svg';
import HalfFilledLeftStar from '@assets/svg/half-filled-left-star.svg';
import HalfFilledRightStar from '@assets/svg/half-filled-right-star.svg';
import { forwardRef } from 'react';
import type { ForwardedRef } from 'react';

import Label from '@components/Label/Label';
import {
  inputContainerStyling,
  starItemStyling,
} from '@components/StarRatingInput/StarRatingInput.style';
import SupportingText from '@components/SupportingText/SupportingText';

const STAR_RATING_EMPTY_LENGTH = 10;

export interface StarRatingInputProps {
  isMobile: boolean;
  label?: string;
  supportingText?: string;
  required?: boolean;
  /** rate는 0~5까지 0.5단위로 입력할 수 있다. */
  rate: 0 | 0.5 | 1 | 1.5 | 2 | 2.5 | 3 | 3.5 | 4 | 4.5 | 5;
  size?: number;
  gap?: number;
  onStarClick: (index: number) => void;
  onStarHover: (index: number) => void;
  onStarHoverOut: (index: number) => void;
}

const StarRatingInput = (
  {
    isMobile = false,
    label,
    supportingText,
    required = false,
    rate = 0,
    size = 24,
    gap = 2,
    onStarClick,
    onStarHover,
    onStarHoverOut,
  }: StarRatingInputProps,
  ref: ForwardedRef<HTMLDivElement>
) => {
  const StarRatingTemp = Array.from({ length: STAR_RATING_EMPTY_LENGTH }, () => '');

  return (
    <>
      {label && <Label required={required}>{label}</Label>}
      <div className="star-box" css={inputContainerStyling(size, gap)} ref={ref}>
        {StarRatingTemp.map((_, index) => {
          if (index % 2 === 0) {
            if (rate > 0 && index + 1 <= rate * 2)
              return (
                // eslint-disable-next-line jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions, jsx-a11y/mouse-events-have-key-events
                <div
                  onClick={() => {
                    onStarClick(index);
                  }}
                  onMouseOver={() => {
                    if (!isMobile) onStarHover(index);
                  }}
                  onMouseOut={() => {
                    if (!isMobile) onStarHoverOut(index);
                  }}
                  key={crypto.randomUUID()}
                >
                  <HalfFilledLeftStar css={starItemStyling} />
                </div>
              );
            return (
              // eslint-disable-next-line jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions, jsx-a11y/mouse-events-have-key-events
              <div
                onClick={() => {
                  onStarClick(index);
                }}
                onMouseOver={() => {
                  if (!isMobile) onStarHover(index);
                }}
                onMouseOut={() => {
                  if (!isMobile) onStarHoverOut(index);
                }}
                key={crypto.randomUUID()}
              >
                <HalfEmptyLeftStar width={size / 2} height={size} css={starItemStyling} />
              </div>
            );
          }
          if (rate > 0 && index + 1 <= rate * 2)
            return (
              // eslint-disable-next-line jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions, jsx-a11y/mouse-events-have-key-events
              <div
                onClick={() => {
                  onStarClick(index);
                }}
                onMouseOver={() => {
                  if (!isMobile) onStarHover(index);
                }}
                onMouseOut={() => {
                  if (!isMobile) onStarHoverOut(index);
                }}
                key={crypto.randomUUID()}
              >
                <HalfFilledRightStar width={size / 2} height={size} css={starItemStyling} />
              </div>
            );
          return (
            // eslint-disable-next-line jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions, jsx-a11y/mouse-events-have-key-events
            <div
              onClick={() => {
                onStarClick(index);
              }}
              onMouseOver={() => {
                if (!isMobile) onStarHover(index);
              }}
              onMouseOut={() => {
                if (!isMobile) onStarHoverOut(index);
              }}
              key={crypto.randomUUID()}
            >
              <HalfEmptyRightStar width={size / 2} height={size} css={starItemStyling} />
            </div>
          );
        })}
      </div>
      {supportingText && <SupportingText>{supportingText}</SupportingText>}
    </>
  );
};

export default forwardRef(StarRatingInput);
