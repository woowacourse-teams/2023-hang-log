import type { TripData } from '@type/trip';

export const sharedTrip: TripData = {
  tripType: 'SHARED',
  id: 1,
  title: '런던 여행',
  startDate: '2023-07-01',
  endDate: '2023-07-03',
  description: '라곤의 좌충우돌 유럽 여행기',
  sharedCode: null,
  isPublished: true,
  writer: {
    nickname: null,
    imageUrl: null,
  },
  isLike: null,
  likeCount: null,
  isWriter: null,
  publishedDate: null,
  imageName: 'default-image.png',
  cities: [
    {
      id: 1,
      name: '런던',
      latitude: 51.509865,
      longitude: -0.118092,
    },
    {
      id: 2,
      name: '애든버러',
      latitude: 55.953251,
      longitude: -3.188267,
    },
    {
      id: 3,
      name: '파리',
      latitude: 48.864716,
      longitude: 2.338629,
    },
  ],
  dayLogs: [
    {
      id: 1,
      title: '런던 방문기',
      ordinal: 1,
      date: '2023-07-01',
      items: [
        {
          id: 1,
          itemType: true,
          title: '피카딜리 서커스',
          ordinal: 1,
          rating: 4,
          memo: '북적북적한 피키달리 서크스. 주변에 맛있는 식당이 없는게 아쉬움.',
          place: {
            id: 1,
            name: '피카딜리 서커스',
            latitude: 51.510067,
            longitude: -0.133869,
            category: {
              id: 1,
              name: '명소',
            },
          },
          expense: {
            id: 1,
            currency: 'GBP',
            amount: 200,
            category: {
              id: 3,
              name: '관광',
            },
          },
          imageNames: [
            'default-image.png',
            'default-image.png',
            'default-image.png',
            'default-image.png',
          ],
        },
        {
          id: 2,
          itemType: true,
          title: '대영박물관',
          ordinal: 2,
          rating: null,
          memo: '세계의 역사를 볼 수 있는 대영박물관',
          place: {
            id: 2,
            name: '대영박물관',
            latitude: 51.51929365,
            longitude: -0.128017721784947,
            category: {
              id: 1,
              name: '명소',
            },
          },
          expense: null,
          imageNames: ['default-image.png'],
        },
        {
          id: 3,
          itemType: false,
          title: '택시',
          ordinal: 3,
          rating: null,
          memo: '대영박물관에서 빅밴으로',
          place: null,
          expense: {
            id: 2,
            currency: 'GBP',
            amount: 20,
            category: {
              id: 3,
              name: '교통',
            },
          },
          imageNames: [],
        },
        {
          id: 10,
          itemType: true,
          title: 'covertGarden',
          ordinal: 1,
          rating: 4.5,
          memo: null,
          place: {
            id: 1,
            name: 'covertGarden',
            latitude: 51.5117,
            longitude: -0.1226,
            category: {
              id: 2,
              name: '공원',
            },
          },
          expense: {
            id: 3,
            currency: 'GBP',
            amount: 50,
            category: {
              id: 2,
              name: '음식',
            },
          },
          imageNames: ['default-image.png'],
        },
      ],
    },
    {
      id: 2,
      title: '애든버러 방문기',
      ordinal: 2,
      date: '2023-07-02',
      items: [
        {
          id: 5,
          itemType: true,
          title: 'LOWDOWN',
          ordinal: 1,
          rating: 4.5,
          memo: null,
          place: {
            id: 4,
            name: 'LOWDOWN',
            latitude: 55.95302,
            longitude: -3.19973,
            category: {
              id: 1,
              name: '카페',
            },
          },
          expense: {
            id: 3,
            currency: 'GBP',
            amount: 50,
            category: {
              id: 2,
              name: '음식',
            },
          },
          imageNames: ['default-image.png'],
        },
        {
          id: 4,
          itemType: false,
          title: '기차',
          ordinal: 2,
          rating: 2,
          memo: '오래걸리고 엄청 추웠다.',
          place: null,
          expense: {
            id: 3,
            currency: 'GBP',
            amount: 300,
            category: {
              id: 3,
              name: '교통',
            },
          },
          imageNames: ['default-image.png'],
        },
      ],
    },
    {
      id: 3,
      title: '',
      ordinal: 3,
      date: '2023-07-03',
      items: [],
    },
    {
      id: 4,
      title: '',
      ordinal: 4,
      date: '2023-07-04',
      items: [],
    },
  ],
};
