import { css } from '@emotion/react';

export interface FlexStylingProps {
  direction?: 'row' | 'column';
  wrap?: 'nowrap' | 'wrap' | 'wrap-reverse';
  basis?: 'auto' | '0' | '200px';
  grow?: string;
  shrink?: string;
  align?:
    | 'normal'
    | 'stretch'
    | 'center'
    | 'start'
    | 'end'
    | 'flex-start'
    | 'flex-end'
    | 'self-start'
    | 'self-end'
    | 'baseline'
    | 'inherit'
    | 'initial'
    | 'unset';
  justify?:
    | 'center'
    | 'start'
    | 'flex-start'
    | 'end'
    | 'flex-end'
    | 'left'
    | 'right'
    | 'normal'
    | 'space-between'
    | 'space-around'
    | 'space-evenly'
    | 'stretch'
    | 'inherit'
    | 'initial'
    | 'revert'
    | 'unset';
  gap?: string;
  margin?: string;
  marginRight?: string;
  marginTop?: string;
  marginLeft?: string;
  marginBottom?: string;
  padding?: string;
  paddingTop?: string;
  paddingRight?: string;
  paddingBottom?: string;
  paddingLeft?: string;
  border?: string;
  borderRadius?: string;
  borderColor?: string;
  borderTop?: string;
  borderRight?: string;
  borderBottom?: string;
  borderLeft?: string;
  width?: string;
  height?: string;
  position?: 'static' | 'absolute' | 'relative' | 'fixed' | 'inherit';
}

export const getFlexStyling = ({
  direction = 'row',
  wrap = 'nowrap',
  basis = 'auto',
  grow = '1',
  shrink = '0',
  align = 'flex-start',
  justify = 'flex-start',
  gap = '0px',
  margin = '0',
  marginRight = '',
  marginTop = '',
  marginLeft = '',
  marginBottom = '',
  padding = '',
  paddingTop = '',
  paddingRight = '',
  paddingBottom = '',
  paddingLeft = '',
  border = '',
  borderRadius = '',
  borderColor = '',
  borderTop = '',
  borderRight = '',
  borderBottom = '',
  borderLeft = '',
  width = '',
  height = '',
  position = 'static',
}: FlexStylingProps) => {
  return css({
    display: 'flex',
    flexDirection: direction,
    flexWrap: wrap,
    flexBasis: basis,
    grow,
    flexShrink: shrink,
    alignItems: align,
    justifyContent: justify,
    gap,
    margin,
    marginRight,
    marginTop,
    marginLeft,
    marginBottom,
    padding,
    paddingTop,
    paddingRight,
    paddingBottom,
    paddingLeft,
    border,
    borderRadius,
    borderColor,
    borderTop,
    borderRight,
    borderBottom,
    borderLeft,
    width,
    height,
    position,
  });
};
