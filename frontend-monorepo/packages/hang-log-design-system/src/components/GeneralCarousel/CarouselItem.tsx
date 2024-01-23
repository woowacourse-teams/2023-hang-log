import { useContext, useEffect, useRef } from 'react';
import type { PropsWithChildren } from 'react';

import { CarouselContext } from '@components/GeneralCarousel/Carousel';
import { carouselItemStyling } from '@components/GeneralCarousel/Carousel.style';

export interface CarouselItemProps extends PropsWithChildren {
  index: number;
}

const CarouselItem = ({ index, children }: CarouselItemProps) => {
  const ref = useRef<HTMLDivElement | null>(null);
  const context = useContext(CarouselContext);

  if (!context) throw Error('Carousel.Item is only available within Carousel.');

  const { width, height, viewIndex, itemRef } = context;

  useEffect(() => {
    if (ref.current) {
      if (index === viewIndex) itemRef.current = ref.current;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [viewIndex]);

  return (
    <div ref={ref} css={carouselItemStyling(width, height)}>
      {children}
    </div>
  );
};

export default CarouselItem;
