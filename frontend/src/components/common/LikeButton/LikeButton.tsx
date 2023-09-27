import { useState } from 'react';

import { useRecoilValue } from 'recoil';

import { clickableLikeStyling } from '@components/common/LikeButton/LikeButton.style';

import { useLikeMutation } from '@hooks/api/useLikeMutation';

import { isLoggedInState } from '@store/auth';

import ClickEmptyLike from '@assets/svg/click-empty-like.svg';
import ClickFilledLike from '@assets/svg/click-filled-like.svg';

interface LikeButtonProps {
  initialState: boolean;
  tripId: string;
  likeCount: number;
  handleLikeCount: (count: number) => void;
}

const LikeButton = ({ initialState, tripId, handleLikeCount, likeCount }: LikeButtonProps) => {
  const likeMutation = useLikeMutation();

  const isLoggedIn = useRecoilValue(isLoggedInState);
  const [isLikeChecked, setisLikeChecked] = useState<boolean>(initialState);

  const updateLikeCount = (isLike: boolean) =>
    isLike ? handleLikeCount(likeCount + 1) : handleLikeCount(likeCount - 1);

  const handleLikeCheck = (isLike: boolean) => {
    const prevLikeCount = likeCount;
    setisLikeChecked(isLike);
    updateLikeCount(isLike);

    likeMutation.mutate(
      { tripId, isLike, isLoggedIn },
      {
        onError: () => {
          setisLikeChecked(!isLike);
          handleLikeCount(prevLikeCount);
        },
      }
    );
  };

  return (
    <div>
      {isLikeChecked ? (
        <ClickFilledLike
          css={clickableLikeStyling}
          onClick={() => {
            handleLikeCheck(false);
          }}
        />
      ) : (
        <ClickEmptyLike
          css={clickableLikeStyling}
          onClick={() => {
            handleLikeCheck(true);
          }}
        />
      )}
    </div>
  );
};

export default LikeButton;
