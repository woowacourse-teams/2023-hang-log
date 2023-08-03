import type { Segment } from '@components/common/DonutChart/DonutChart';

export const calculateTotalPercentage = (segments: Segment[]) => {
  return segments.reduce((sum, segment) => sum + segment.percentage, 0);
};

export const calculateDonutChartSegmentData = (
  index: number,
  cumulativePercentages: number[],
  segment: Segment,
  size: number,
  strokeWidth: number
) => {
  const startAngle = index === 0 ? 0 : (cumulativePercentages[index - 1] / 100) * 360;
  const endAngle = segment.percentage === 100 ? 359.999 : (segment.percentage / 100) * 360;
  const largeArcFlag = endAngle > 180 ? 1 : 0;

  const x1 = size / 2 + (size / 2 - strokeWidth / 2) * Math.cos((startAngle * Math.PI) / 180);
  const y1 = size / 2 + (size / 2 - strokeWidth / 2) * Math.sin((startAngle * Math.PI) / 180);
  const x2 =
    size / 2 + (size / 2 - strokeWidth / 2) * Math.cos(((startAngle + endAngle) * Math.PI) / 180);
  const y2 =
    size / 2 + (size / 2 - strokeWidth / 2) * Math.sin(((startAngle + endAngle) * Math.PI) / 180);

  const pathData = `M ${x1} ${y1} A ${size / 2 - strokeWidth / 2} ${
    size / 2 - strokeWidth / 2
  } 0 ${largeArcFlag} 1 ${x2} ${y2}`;

  // ! 이 값을 조정해서 차트 위 텍스트 위치 조정
  const labelRadius = size / 2 - strokeWidth / 2 - 2;

  const labelX = size / 2 + labelRadius * Math.cos(((startAngle + endAngle / 2) * Math.PI) / 180);
  const labelY = size / 2 + labelRadius * Math.sin(((startAngle + endAngle / 2) * Math.PI) / 180);

  return { pathData, labelX, labelY };
};
