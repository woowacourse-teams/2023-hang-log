import type { ComponentPropsWithoutRef } from 'react';
import { useState } from 'react';

import { useRecoilValue } from 'recoil';

import { useLikeMutation } from '@hooks/api/useLikeMutation';

import { isLoggedInState } from '@store/auth';

import ClickEmptyLike from '@assets/svg/click-empty-like.svg';
import ClickFilledLike from '@assets/svg/click-filled-like.svg';

interface LikeButtonProps extends ComponentPropsWithoutRef<'div'> {
  initialState: boolean;
  tripId: string;
  likeCount: number;
  handleLikeCount: (count: number) => void;
}

const LikeButton = ({
  initialState,
  tripId,
  handleLikeCount,
  likeCount,
  ...attribute
}: LikeButtonProps) => {
  const likeMutation = useLikeMutation();

  const isLoggedIn = useRecoilValue(isLoggedInState);
  const [isLikeChecked, setIsLikeChecked] = useState<boolean>(initialState);

  const updateLikeCount = (isLike: boolean) =>
    isLike ? handleLikeCount(likeCount + 1) : handleLikeCount(likeCount - 1);

  const handleLikeCheck = (isLike: boolean) => {
    const prevLikeCount = likeCount;
    setIsLikeChecked(isLike);
    updateLikeCount(isLike);

    likeMutation.mutate(
      { tripId, isLike, isLoggedIn },
      {
        onError: () => {
          setIsLikeChecked(!isLike);
          handleLikeCount(prevLikeCount);
        },
      }
    );
  };

  return (
    <div {...attribute}>
      {isLikeChecked ? (
        <ClickFilledLike
          onClick={(e) => {
            e.stopPropagation();
            handleLikeCheck(false);
          }}
        />
      ) : (
        <ClickEmptyLike
          onClick={(e) => {
            e.stopPropagation();
            handleLikeCheck(true);
          }}
        />
      )}
    </div>
  );
};

export default LikeButton;
