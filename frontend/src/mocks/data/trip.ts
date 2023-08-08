import type { TripData } from '@type/trip';

export const trip: TripData = {
  id: 1,
  title: '런던 여행',
  startDate: '2023-07-01',
  endDate: '2023-07-03',
  description: '라곤의 좌충우돌 유럽 여행기',
  imageUrl:
    'https://a.cdn-hotels.com/gdcs/production153/d1371/e6c1f55e-51ac-41d5-8c63-2d0c63faf59e.jpg',
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
          imageUrls: [
            'https://upload.wikimedia.org/wikipedia/commons/f/f9/Open_Happiness_Piccadilly_Circus_Blue-Pink_Hour_120917-1126-jikatu.jpg',
            'https://e3.365dm.com/17/10/2048x1152/skynews-piccadilly-piccadilly-circus_4131587.jpg',
            'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/15/9e/a5/6f/regent-str.jpg?w=1200&h=-1&s=1',
            'https://flashbak.com/wp-content/uploads/2017/01/Piccadilly-by-Night-London-1960-by-Elmar-Ludwig.jpg',
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
          imageUrls: ['https://pbs.twimg.com/media/D8sOPlgXUAACySN.jpg'],
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
          imageUrls: [],
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
          imageUrls: [
            'https://lh3.googleusercontent.com/p/AF1QipOirgDoMN4u1mwho_3Nh_4hS6IehGlewiHtcQLI=s1360-w1360-h1020',
          ],
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
          imageUrls: [
            'https://lh3.googleusercontent.com/p/AF1QipOirgDoMN4u1mwho_3Nh_4hS6IehGlewiHtcQLI=s1360-w1360-h1020',
          ],
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
          imageUrls: [
            'https://www.thetrainline.com/cms/media/4626/southeastern-javelin-train-st-pancras-intl-desktop_1x.jpg',
          ],
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
