import { getSpinnerStyling } from './Spinner.style';

export interface SpinnerProps {
  timing?: number;
  size?: number;
  width?: number;
  disabled?: boolean;
}

const Spinner = ({ timing = 1, size = 50, width = 5, disabled = false }: SpinnerProps) => (
  <div css={getSpinnerStyling({ timing, size, width, disabled })} />
);

export default Spinner;
