import { rest } from 'msw';

export const handlers = [
  rest.get('/trips', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        trips: [
          {
            id: 1,
            title: '런던 여행',
            imageUrls: 'https://res.klook.com/image/upload/Mobile/City/n9sn4fajwa1skldmdeex.jpg',
            cities: [
              {
                id: 1,
                name: '런던',
              },
              {
                id: 2,
                name: '애든버러',
              },
            ],
            startDate: '2023-06-13',
            endDate: '2023-06-20',
            description: '어쩌구 저쩌구 좌충우돌 라곤의 런던 여행기',
          },
          {
            id: 2,
            title: '서울 여행',
            imageUrls:
              'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1624386887478-9Z3XA27D8WFVDWKW00QS/20201230173806551_JRT8E1VC.png',
            cities: [
              {
                id: 1,
                name: '대한민국',
              },
              {
                id: 2,
                name: '서울',
              },
            ],
            startDate: '2023-07-13',
            endDate: '2023-07-20',
            description: '어쩌구 저쩌구 좌충우돌 라곤의 서울 여행기',
          },
          {
            id: 3,
            title: '강릉 여행',
            imageUrls:
              'https://a.cdn-hotels.com/gdcs/production144/d992/418cd5c1-7f91-4c44-9f39-3016b033eaa1.jpg?impolicy=fcrop&w=800&h=533&q=medium',
            cities: [
              {
                id: 1,
                name: '대한민국',
              },
              {
                id: 2,
                name: '강원도',
              },
              {
                id: 3,
                name: '강릉',
              },
            ],
            startDate: '2021-01-13',
            endDate: '2021-01-17',
            description: '어쩌구 저쩌구 좌충우돌 라곤의 강릉 여행기',
          },
        ],
      })
    );
  }),
];
