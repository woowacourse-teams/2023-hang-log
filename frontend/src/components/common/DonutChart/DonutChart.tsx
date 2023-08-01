import { Theme } from 'hang-log-design-system';

import { calculateDonutChartSegmentData } from '@utils/chart';

export interface Segment {
  id: number;
  percentage: number;
  color: string;
}

interface DonutChartProps {
  segments: Segment[];
  size: number;
  strokeWidth: number;
}

const DonutChart: React.FC<DonutChartProps> = ({ segments, size, strokeWidth }) => {
  const totalPercentage = segments.reduce((sum, segment) => sum + segment.percentage, 0);

  const remainingPercentage = 100 - totalPercentage;

  const dataWithFill =
    totalPercentage === 100
      ? segments
      : [...segments, { id: -1, percentage: remainingPercentage, color: Theme.color.gray200 }];

  const cumulativePercentages: number[] = dataWithFill.reduce((acc: number[], segment: Segment) => {
    const lastPercentage = acc.length > 0 ? acc[acc.length - 1] : 0;
    const accumulatedPercentage = lastPercentage + segment.percentage;
    acc.push(accumulatedPercentage);

    return acc;
  }, []);

  return (
    <div style={{ width: `${size}px`, height: `${size}px`, position: 'relative' }}>
      <svg viewBox={`0 0 ${size} ${size}`}>
        {dataWithFill.map((segment, index) => {
          const { pathData, labelX, labelY } = calculateDonutChartSegmentData(
            index,
            cumulativePercentages,
            segment,
            size,
            strokeWidth
          );

          return (
            <g key={segment.id}>
              <path d={pathData} fill="none" stroke={segment.color} strokeWidth={strokeWidth} />
              {segment.percentage >= 5 && (
                <text
                  x={labelX}
                  y={labelY}
                  textAnchor="middle"
                  dominantBaseline="middle"
                  fontSize="16px"
                  fill={Theme.color.gray800}
                >
                  {segment.percentage}%
                </text>
              )}
            </g>
          );
        })}
      </svg>
    </div>
  );
};

export default DonutChart;
