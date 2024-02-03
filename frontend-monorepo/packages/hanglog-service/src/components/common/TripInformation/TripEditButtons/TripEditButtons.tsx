import { useNavigate } from 'react-router-dom';

import { Button } from 'hang-log-design-system';

import { editButtonStyling } from '@components/common/TripInformation/TripEditButtons/TripEditButtons.style';

interface TripEditButtonsProps {
  tripId: string;
  openEditModal?: () => void;
}

const TripEditButtons = ({ tripId, openEditModal }: TripEditButtonsProps) => {
  const navigate = useNavigate();

  return (
    <>
      <Button onClick={openEditModal} css={editButtonStyling} variant="outline" size="small">
        여행 정보 수정
      </Button>
      <Button variant="primary" size="small" onClick={() => navigate(`/trip/${tripId}`)}>
        완료
      </Button>
    </>
  );
};

export default TripEditButtons;
