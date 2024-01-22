import { css } from '@emotion/react';
import type { MouseEvent } from 'react';
import { match } from 'ts-pattern';

import { Theme } from '@styles/Theme';

interface DotsProps {
  imageLength: number;
  activeNumber: number;
  moveImage: (imageNumber: number) => void;
}

const Dots = ({ imageLength, activeNumber, moveImage }: DotsProps) => {
  const images = Array.from({ length: imageLength }, () => '');

  return (
    <div css={dotContainerStyling}>
      {images.map((_, index) =>
        match(activeNumber === index)
          .with(true, () => (
            <button
              type="button"
              key={crypto.randomUUID()}
              css={dotStyle(true)}
              onClick={(e: MouseEvent<HTMLButtonElement>) => {
                e.stopPropagation();
                moveImage(index);
              }}
            />
          ))
          .otherwise(() => (
            <button
              type="button"
              key={crypto.randomUUID()}
              css={dotStyle(false)}
              onClick={(e: MouseEvent<HTMLButtonElement>) => {
                e.stopPropagation();
                moveImage(index);
              }}
            />
          ))
      )}
    </div>
  );
};

export default Dots;

const dotContainerStyling = css({
  position: 'absolute',
  display: 'flex',
  gap: Theme.spacer.spacing2,

  left: '50%',
  bottom: Theme.spacer.spacing3,

  transform: 'translateX(-50%)',
  transition: 'opacity .1s ease-in',

  cursor: 'pointer',

  '.image-carousel-container:hover &': {
    opacity: 1,
  },
});

const dotStyle = (isSelected: boolean) =>
  css({
    width: '6px',
    height: '6px',

    backgroundColor: Theme.color.white,
    borderRadius: '50%',
    border: 'none',

    opacity: isSelected ? 1 : 0.6,
    cursor: 'pointer',
  });
