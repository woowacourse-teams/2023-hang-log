import { Box, Flex, Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';

import TripsItem from '@components/trips/TripsItem/TripsItem';
import {
  TripsItemGridBoxStyling,
  TripsToggleGroupStyling,
} from '@components/trips/TripsItemList/TripsItemList.style';

const dummy = [
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
  {
    coverImage:
      'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
    badgeName: ['대한민국', '서울', '서울', '서울', '서울', '서울', '서울'],
    itemName: '라곤의 서울 여행',
    duration: '2023.07.03-2023.07.04',
    description: '서울 여행을 떠나봐요~',
  },
];

const TripssItemList = () => {
  const { selected, handleSelectClick } = useSelect('등록순');
  return (
    <>
      <Flex styles={{ justify: 'right', paddingRight: '60px' }} css={TripsToggleGroupStyling}>
        <ToggleGroup>
          <Toggle
            text="등록순"
            toggleId="등록순"
            selectedId={selected}
            changeSelect={handleSelectClick}
          />
          <Toggle
            text="날짜순"
            toggleId="날짜순"
            selectedId={selected}
            changeSelect={handleSelectClick}
          />
        </ToggleGroup>
      </Flex>
      <Box css={TripsItemGridBoxStyling}>
        {dummy.map((data) => {
          return (
            <TripsItem
              coverImage={data.coverImage}
              badgeName={data.badgeName}
              itemName={data.itemName}
              duration={data.duration}
              description={data.description}
            />
          );
        })}
      </Box>
    </>
  );
};

export default TripssItemList;
