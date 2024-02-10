import type { CurrencyData } from '@type/currency';

export const currencyListData = (page: number, size: number) => {
  const lastPageIndex = Math.ceil(currencies.length / size) - 1;
  const listData = currencies.slice(page * size, (page + 1) * size);

  return {
    lastPageIndex: lastPageIndex,
    currencies: listData,
  };
};

export const currencies: CurrencyData[] = [
  {
    id: 1,
    date: '2023-05-15',
    chf: 1484.25,
    cny: 191.86,
    eur: 1447.09,
    gbp: 1660.07,
    hkd: 170.05,
    jpy: 982.21,
    krw: 1,
    sgd: 996.26,
    thb: 39.32,
    usd: 1333.6,
  },
  {
    id: 2,
    date: '2023-05-16',
    chf: 1493.83,
    cny: 192.01,
    eur: 1454.79,
    gbp: 1675.59,
    hkd: 170.67,
    jpy: 983.57,
    krw: 1,
    sgd: 1001.12,
    thb: 39.6,
    usd: 1337.8,
  },
  {
    id: 3,
    date: '2023-05-17',
    chf: 1490.16,
    cny: 191.72,
    eur: 1451.16,
    gbp: 1667.46,
    hkd: 170.44,
    jpy: 979.29,
    krw: 1,
    sgd: 996.61,
    thb: 39.18,
    usd: 1336.0,
  },
  {
    id: 4,
    date: '2023-05-18',
    chf: 1490.62,
    cny: 191.29,
    eur: 1451.64,
    gbp: 1672.58,
    hkd: 171.03,
    jpy: 973.44,
    krw: 1,
    sgd: 997.84,
    thb: 39.1,
    usd: 1339.4,
  },
  {
    id: 5,
    date: '2023-05-19',
    chf: 1472.92,
    cny: 189.72,
    eur: 1436.12,
    gbp: 1654.35,
    hkd: 170.27,
    jpy: 962.0,
    krw: 1,
    sgd: 988.83,
    thb: 38.71,
    usd: 1332.7,
  },
  {
    id: 6,
    date: '2023-05-22',
    chf: 1481.83,
    cny: 188.66,
    eur: 1440.69,
    gbp: 1658.48,
    hkd: 170.28,
    jpy: 967.34,
    krw: 1,
    sgd: 990.07,
    thb: 38.79,
    usd: 1331.2,
  },
  {
    id: 7,
    date: '2023-05-23',
    chf: 1468.93,
    cny: 187.69,
    eur: 1426.01,
    gbp: 1640.37,
    hkd: 168.5,
    jpy: 951.9,
    krw: 1,
    sgd: 979.69,
    thb: 38.28,
    usd: 1319.1,
  },
  {
    id: 8,
    date: '2023-05-24',
    chf: 1455.09,
    cny: 185.76,
    eur: 1412.77,
    gbp: 1628.76,
    hkd: 167.33,
    jpy: 946.59,
    krw: 1,
    sgd: 973.72,
    thb: 37.93,
    usd: 1311.4,
  },
  {
    id: 9,
    date: '2023-05-25',
    chf: 1456.69,
    cny: 186.32,
    eur: 1416.97,
    gbp: 1629.48,
    hkd: 168.27,
    jpy: 946.31,
    krw: 1,
    sgd: 976.61,
    thb: 38.05,
    usd: 1317.5,
  },
  {
    id: 10,
    date: '2023-05-26',
    chf: 1461.67,
    cny: 186.93,
    eur: 1419.48,
    gbp: 1630.96,
    hkd: 168.91,
    jpy: 945.72,
    krw: 1,
    sgd: 977.0,
    thb: 38.09,
    usd: 1323.4,
  },
  {
    id: 11,
    date: '2023-05-30',
    chf: 1465.72,
    cny: 187.25,
    eur: 1419.45,
    gbp: 1637.91,
    hkd: 169.33,
    jpy: 943.72,
    krw: 1,
    sgd: 979.21,
    thb: 38.2,
    usd: 1325.6,
  },
  {
    id: 12,
    date: '2023-05-31',
    chf: 1458.98,
    cny: 186.31,
    eur: 1418.99,
    gbp: 1640.72,
    hkd: 168.77,
    jpy: 945.98,
    krw: 1,
    sgd: 978.94,
    thb: 38.12,
    usd: 1322.2,
  },
  {
    id: 13,
    date: '2023-06-01',
    chf: 1451.99,
    cny: 185.92,
    eur: 1413.39,
    gbp: 1644.93,
    hkd: 168.78,
    jpy: 950.01,
    krw: 1,
    sgd: 977.91,
    thb: 38.1,
    usd: 1321.6,
  },
  {
    id: 14,
    date: '2023-06-02',
    chf: 1458.53,
    cny: 185.58,
    eur: 1421.54,
    gbp: 1654.51,
    hkd: 168.65,
    jpy: 951.75,
    krw: 1,
    sgd: 980.4,
    thb: 38.21,
    usd: 1320.7,
  },
  {
    id: 15,
    date: '2023-06-05',
    chf: 1439.35,
    cny: 184.32,
    eur: 1400.55,
    gbp: 1627.56,
    hkd: 167.0,
    jpy: 934.02,
    krw: 1,
    sgd: 969.01,
    thb: 37.59,
    usd: 1308.8,
  },
  {
    id: 16,
    date: '2023-06-07',
    chf: 1441.49,
    cny: 183.99,
    eur: 1398.97,
    gbp: 1625.43,
    hkd: 166.81,
    jpy: 937.28,
    krw: 1,
    sgd: 970.37,
    thb: 37.61,
    usd: 1308.3,
  },
  {
    id: 17,
    date: '2023-06-08',
    chf: 1429.28,
    cny: 182.46,
    eur: 1391.21,
    gbp: 1617.43,
    hkd: 165.86,
    jpy: 928.63,
    krw: 1,
    sgd: 964.23,
    thb: 37.32,
    usd: 1300.5,
  },
  {
    id: 18,
    date: '2023-06-09',
    chf: 1453.08,
    cny: 182.72,
    eur: 1408.5,
    gbp: 1640.72,
    hkd: 166.67,
    jpy: 940.69,
    krw: 1,
    sgd: 972.92,
    thb: 37.73,
    usd: 1306.1,
  },
  {
    id: 19,
    date: '2023-06-12',
    chf: 1432.81,
    cny: 181.78,
    eur: 1391.5,
    gbp: 1628.47,
    hkd: 165.19,
    jpy: 928.81,
    krw: 1,
    sgd: 963.75,
    thb: 37.42,
    usd: 1294.9,
  },
  {
    id: 20,
    date: '2023-06-13',
    chf: 1421.06,
    cny: 180.59,
    eur: 1389.55,
    gbp: 1615.75,
    hkd: 164.78,
    jpy: 925.75,
    krw: 1,
    sgd: 961.03,
    thb: 37.26,
    usd: 1291.1,
  },
  {
    id: 21,
    date: '2023-06-14',
    chf: 1411.45,
    cny: 178.19,
    eur: 1378.55,
    gbp: 1610.8,
    hkd: 163.08,
    jpy: 911.49,
    krw: 1,
    sgd: 951.76,
    thb: 36.85,
    usd: 1277.5,
  },
  {
    id: 22,
    date: '2023-06-15',
    chf: 1416.25,
    cny: 177.98,
    eur: 1382.18,
    gbp: 1615.67,
    hkd: 162.94,
    jpy: 911.0,
    krw: 1,
    sgd: 951.21,
    thb: 36.76,
    usd: 1275.9,
  },
  {
    id: 23,
    date: '2023-06-16',
    chf: 1436.22,
    cny: 178.67,
    eur: 1401.84,
    gbp: 1637.25,
    hkd: 163.66,
    jpy: 913.48,
    krw: 1,
    sgd: 958.05,
    thb: 37.0,
    usd: 1280.1,
  },
  {
    id: 24,
    date: '2023-06-19',
    chf: 1423.86,
    cny: 178.66,
    eur: 1393.53,
    gbp: 1634.03,
    hkd: 162.84,
    jpy: 897.49,
    krw: 1,
    sgd: 952.26,
    thb: 36.72,
    usd: 1273.5,
  },
  {
    id: 25,
    date: '2023-06-20',
    chf: 1429.75,
    cny: 179.46,
    eur: 1398.84,
    gbp: 1639.36,
    hkd: 163.85,
    jpy: 902.31,
    krw: 1,
    sgd: 955.28,
    thb: 36.84,
    usd: 1280.7,
  },
  {
    id: 26,
    date: '2023-06-21',
    chf: 1428.48,
    cny: 178.29,
    eur: 1400.35,
    gbp: 1636.6,
    hkd: 163.85,
    jpy: 907.46,
    krw: 1,
    sgd: 954.62,
    thb: 36.8,
    usd: 1282.2,
  },
  {
    id: 27,
    date: '2023-06-22',
    chf: 1447.38,
    cny: 179.54,
    eur: 1419.68,
    gbp: 1649.5,
    hkd: 164.98,
    jpy: 911.5,
    krw: 1,
    sgd: 963.84,
    thb: 37.14,
    usd: 1291.5,
  },
  {
    id: 28,
    date: '2023-06-23',
    chf: 1443.07,
    cny: 179.71,
    eur: 1414.79,
    gbp: 1646.15,
    hkd: 164.92,
    jpy: 902.67,
    krw: 1,
    sgd: 960.29,
    thb: 36.74,
    usd: 1291.4,
  },
  {
    id: 29,
    date: '2023-06-26',
    chf: 1453.19,
    cny: 180.57,
    eur: 1419.85,
    gbp: 1656.59,
    hkd: 166.3,
    jpy: 907.01,
    krw: 1,
    sgd: 963.13,
    thb: 36.96,
    usd: 1302.2,
  },
  {
    id: 30,
    date: '2023-06-27',
    chf: 1455.87,
    cny: 180.66,
    eur: 1422.45,
    gbp: 1657.59,
    hkd: 166.51,
    jpy: 909.11,
    krw: 1,
    sgd: 963.32,
    thb: 37.02,
    usd: 1303.8,
  },
  {
    id: 31,
    date: '2023-06-28',
    chf: 1458.1,
    cny: 180.45,
    eur: 1427.94,
    gbp: 1661.13,
    hkd: 166.35,
    jpy: 905.34,
    krw: 1,
    sgd: 965.72,
    thb: 36.95,
    usd: 1303.1,
  },
  {
    id: 32,
    date: '2023-06-29',
    chf: 1454.64,
    cny: 180.28,
    eur: 1423.84,
    gbp: 1648.64,
    hkd: 166.52,
    jpy: 903.35,
    krw: 1,
    sgd: 964.22,
    thb: 36.65,
    usd: 1304.3,
  },
  {
    id: 33,
    date: '2023-06-30',
    chf: 1459.72,
    cny: 181.05,
    eur: 1426.55,
    gbp: 1655.51,
    hkd: 167.48,
    jpy: 906.97,
    krw: 1,
    sgd: 967.96,
    thb: 36.84,
    usd: 1312.8,
  },
  {
    id: 34,
    date: '2023-07-03',
    chf: 1474.03,
    cny: 181.77,
    eur: 1439.47,
    gbp: 1675.37,
    hkd: 168.36,
    jpy: 913.87,
    krw: 1,
    sgd: 975.56,
    thb: 37.39,
    usd: 1319.4,
  },
  {
    id: 35,
    date: '2023-07-04',
    chf: 1461.48,
    cny: 180.88,
    eur: 1429.8,
    gbp: 1662.46,
    hkd: 167.22,
    jpy: 905.57,
    krw: 1,
    sgd: 969.65,
    thb: 37.39,
    usd: 1310.0,
  },
  {
    id: 36,
    date: '2023-07-05',
    chf: 1451.01,
    cny: 179.53,
    eur: 1416.38,
    gbp: 1654.85,
    hkd: 166.22,
    jpy: 900.92,
    krw: 1,
    sgd: 964.62,
    thb: 37.32,
    usd: 1301.7,
  },
  {
    id: 37,
    date: '2023-07-06',
    chf: 1447.42,
    cny: 179.87,
    eur: 1411.89,
    gbp: 1652.47,
    hkd: 166.31,
    jpy: 900.05,
    krw: 1,
    sgd: 961.03,
    thb: 37.19,
    usd: 1300.8,
  },
  {
    id: 38,
    date: '2023-07-07',
    chf: 1456.6,
    cny: 179.44,
    eur: 1420.36,
    gbp: 1661.69,
    hkd: 166.68,
    jpy: 905.51,
    krw: 1,
    sgd: 963.74,
    thb: 37.08,
    usd: 1303.8,
  },
  {
    id: 39,
    date: '2023-07-10',
    chf: 1470.12,
    cny: 180.29,
    eur: 1433.39,
    gbp: 1677.85,
    hkd: 167.02,
    jpy: 919.11,
    krw: 1,
    sgd: 970.74,
    thb: 37.18,
    usd: 1307.3,
  },
  {
    id: 40,
    date: '2023-07-11',
    chf: 1472.55,
    cny: 180.16,
    eur: 1434.44,
    gbp: 1677.21,
    hkd: 166.56,
    jpy: 922.82,
    krw: 1,
    sgd: 969.84,
    thb: 37.17,
    usd: 1303.8,
  },
  {
    id: 41,
    date: '2023-07-12',
    chf: 1473.43,
    cny: 179.26,
    eur: 1426.12,
    gbp: 1675.41,
    hkd: 165.45,
    jpy: 923.25,
    krw: 1,
    sgd: 965.95,
    thb: 37.23,
    usd: 1295.0,
  },
  {
    id: 42,
    date: '2023-07-13',
    chf: 1489.25,
    cny: 179.43,
    eur: 1437.97,
    gbp: 1677.72,
    hkd: 165.02,
    jpy: 932.92,
    krw: 1,
    sgd: 970.9,
    thb: 37.25,
    usd: 1291.4,
  },
  {
    id: 43,
    date: '2023-07-14',
    chf: 1485.77,
    cny: 177.82,
    eur: 1432.66,
    gbp: 1676.16,
    hkd: 163.16,
    jpy: 925.22,
    krw: 1,
    sgd: 965.25,
    thb: 36.94,
    usd: 1276.2,
  },
  {
    id: 44,
    date: '2023-07-17',
    chf: 1468.28,
    cny: 177.15,
    eur: 1420.92,
    gbp: 1656.68,
    hkd: 161.97,
    jpy: 911.99,
    krw: 1,
    sgd: 957.6,
    thb: 36.48,
    usd: 1265.8,
  },
  {
    id: 45,
    date: '2023-07-18',
    chf: 1471.61,
    cny: 176.69,
    eur: 1423.1,
    gbp: 1655.43,
    hkd: 162.03,
    jpy: 912.87,
    krw: 1,
    sgd: 957.9,
    thb: 36.6,
    usd: 1266.1,
  },
  {
    id: 46,
    date: '2023-07-19',
    chf: 1471.17,
    cny: 175.66,
    eur: 1416.97,
    gbp: 1644.75,
    hkd: 161.47,
    jpy: 908.31,
    krw: 1,
    sgd: 953.88,
    thb: 37.04,
    usd: 1261.6,
  },
  {
    id: 47,
    date: '2023-07-20',
    chf: 1474.4,
    cny: 175.55,
    eur: 1417.63,
    gbp: 1636.79,
    hkd: 162.06,
    jpy: 906.03,
    krw: 1,
    sgd: 955.13,
    thb: 37.18,
    usd: 1265.4,
  },
  {
    id: 48,
    date: '2023-07-21',
    chf: 1460.99,
    cny: 175.97,
    eur: 1409.66,
    gbp: 1629.4,
    hkd: 162.0,
    jpy: 904.37,
    krw: 1,
    sgd: 953.95,
    thb: 37.01,
    usd: 1265.8,
  },
  {
    id: 49,
    date: '2023-07-24',
    chf: 1480.02,
    cny: 178.55,
    eur: 1426.4,
    gbp: 1647.5,
    hkd: 163.95,
    jpy: 904.61,
    krw: 1,
    sgd: 963.18,
    thb: 37.22,
    usd: 1281.7,
  },
  {
    id: 50,
    date: '2023-07-25',
    chf: 1475.8,
    cny: 178.3,
    eur: 1420.14,
    gbp: 1645.45,
    hkd: 164.31,
    jpy: 907.12,
    krw: 1,
    sgd: 963.78,
    thb: 37.17,
    usd: 1283.8,
  },
  {
    id: 51,
    date: '2023-07-26',
    chf: 1481.43,
    cny: 178.87,
    eur: 1415.37,
    gbp: 1651.4,
    hkd: 163.95,
    jpy: 908.78,
    krw: 1,
    sgd: 964.56,
    thb: 37.21,
    usd: 1280.7,
  },
  {
    id: 52,
    date: '2023-07-27',
    chf: 1484.96,
    cny: 178.64,
    eur: 1417.38,
    gbp: 1653.49,
    hkd: 163.91,
    jpy: 910.59,
    krw: 1,
    sgd: 964.66,
    thb: 37.37,
    usd: 1278.7,
  },
  {
    id: 53,
    date: '2023-07-28',
    chf: 1465.11,
    cny: 178.33,
    eur: 1397.94,
    gbp: 1629.32,
    hkd: 163.19,
    jpy: 914.4,
    krw: 1,
    sgd: 956.69,
    thb: 36.97,
    usd: 1273.4,
  },
  {
    id: 54,
    date: '2023-07-31',
    chf: 1471.52,
    cny: 178.49,
    eur: 1411.33,
    gbp: 1645.38,
    hkd: 164.18,
    jpy: 908.28,
    krw: 1,
    sgd: 961.18,
    thb: 37.37,
    usd: 1280.0,
  },
  {
    id: 55,
    date: '2023-08-01',
    chf: 1461.37,
    cny: 178.29,
    eur: 1401.12,
    gbp: 1635.18,
    hkd: 163.34,
    jpy: 895.18,
    krw: 1,
    sgd: 957.92,
    thb: 37.18,
    usd: 1273.8,
  },
  {
    id: 56,
    date: '2023-08-02',
    chf: 1466.42,
    cny: 178.63,
    eur: 1409.67,
    gbp: 1637.82,
    hkd: 164.31,
    jpy: 896.0,
    krw: 1,
    sgd: 959.29,
    thb: 37.31,
    usd: 1280.7,
  },
  {
    id: 57,
    date: '2023-08-03',
    chf: 1474.01,
    cny: 179.95,
    eur: 1415.06,
    gbp: 1644.18,
    hkd: 165.74,
    jpy: 902.78,
    krw: 1,
    sgd: 963.99,
    thb: 37.51,
    usd: 1293.0,
  },
  {
    id: 58,
    date: '2023-08-04',
    chf: 1485.79,
    cny: 180.47,
    eur: 1422.45,
    gbp: 1651.1,
    hkd: 166.35,
    jpy: 911.02,
    krw: 1,
    sgd: 968.57,
    thb: 37.59,
    usd: 1298.8,
  },
  {
    id: 59,
    date: '2023-08-07',
    chf: 1491.71,
    cny: 181.53,
    eur: 1434.68,
    gbp: 1662.47,
    hkd: 166.93,
    jpy: 919.05,
    krw: 1,
    sgd: 973.24,
    thb: 37.58,
    usd: 1303.9,
  },
  {
    id: 60,
    date: '2023-08-08',
    chf: 1494.24,
    cny: 181.16,
    eur: 1434.97,
    gbp: 1666.77,
    hkd: 167.04,
    jpy: 915.58,
    krw: 1,
    sgd: 972.52,
    thb: 37.42,
    usd: 1304.1,
  },
  {
    id: 61,
    date: '2023-08-09',
    chf: 1498.49,
    cny: 181.9,
    eur: 1437.95,
    gbp: 1672.71,
    hkd: 167.98,
    jpy: 916.08,
    krw: 1,
    sgd: 973.88,
    thb: 37.48,
    usd: 1312.6,
  },
  {
    id: 62,
    date: '2023-08-10',
    chf: 1503.11,
    cny: 182.64,
    eur: 1446.97,
    gbp: 1676.68,
    hkd: 168.56,
    jpy: 917.49,
    krw: 1,
    sgd: 979.27,
    thb: 37.59,
    usd: 1318.3,
  },
  {
    id: 63,
    date: '2023-08-11',
    chf: 1502.4,
    cny: 182.32,
    eur: 1446.72,
    gbp: 1669.22,
    hkd: 168.46,
    jpy: 909.77,
    krw: 1,
    sgd: 976.25,
    thb: 37.5,
    usd: 1317.3,
  },
  {
    id: 64,
    date: '2023-08-14',
    chf: 1506.39,
    cny: 182.59,
    eur: 1445.68,
    gbp: 1676.48,
    hkd: 168.98,
    jpy: 911.7,
    krw: 1,
    sgd: 976.42,
    thb: 37.64,
    usd: 1321.1,
  },
  {
    id: 65,
    date: '2023-08-16',
    chf: 1514.99,
    cny: 183.28,
    eur: 1451.52,
    gbp: 1690.75,
    hkd: 170.16,
    jpy: 914.26,
    krw: 1,
    sgd: 980.01,
    thb: 37.61,
    usd: 1331.3,
  },
  {
    id: 66,
    date: '2023-08-17',
    chf: 1520.8,
    cny: 182.83,
    eur: 1455.87,
    gbp: 1703.79,
    hkd: 170.89,
    jpy: 914.8,
    krw: 1,
    sgd: 984.23,
    thb: 37.72,
    usd: 1338.3,
  },
  {
    id: 67,
    date: '2023-08-18',
    chf: 1527.41,
    cny: 182.8,
    eur: 1458.79,
    gbp: 1709.94,
    hkd: 171.34,
    jpy: 920.76,
    krw: 1,
    sgd: 987.67,
    thb: 37.77,
    usd: 1341.6,
  },
  {
    id: 68,
    date: '2023-08-21',
    chf: 1514.9,
    cny: 183.27,
    eur: 1453.54,
    gbp: 1702.68,
    hkd: 170.68,
    jpy: 919.75,
    krw: 1,
    sgd: 984.93,
    thb: 37.78,
    usd: 1336.9,
  },
  {
    id: 69,
    date: '2023-08-22',
    chf: 1527.03,
    cny: 183.13,
    eur: 1461.83,
    gbp: 1711.75,
    hkd: 171.13,
    jpy: 917.48,
    krw: 1,
    sgd: 988.18,
    thb: 38.11,
    usd: 1341.5,
  },
  {
    id: 70,
    date: '2023-08-23',
    chf: 1520.9,
    cny: 183.37,
    eur: 1452.02,
    gbp: 1704.23,
    hkd: 170.8,
    jpy: 918.05,
    krw: 1,
    sgd: 986.04,
    thb: 38.2,
    usd: 1338.7,
  },
  {
    id: 71,
    date: '2023-08-24',
    chf: 1524.35,
    cny: 183.29,
    eur: 1453.6,
    gbp: 1701.74,
    hkd: 170.65,
    jpy: 924.38,
    krw: 1,
    sgd: 989.32,
    thb: 38.28,
    usd: 1338.0,
  },
  {
    id: 72,
    date: '2023-08-25',
    chf: 1495.37,
    cny: 181.54,
    eur: 1429.52,
    gbp: 1666.78,
    hkd: 168.85,
    jpy: 906.88,
    krw: 1,
    sgd: 975.83,
    thb: 37.8,
    usd: 1324.0,
  },
  {
    id: 73,
    date: '2023-08-28',
    chf: 1498.81,
    cny: 182.1,
    eur: 1431.93,
    gbp: 1668.86,
    hkd: 169.13,
    jpy: 905.0,
    krw: 1,
    sgd: 978.25,
    thb: 37.77,
    usd: 1326.6,
  },
  {
    id: 74,
    date: '2023-08-29',
    chf: 1497.23,
    cny: 181.65,
    eur: 1431.8,
    gbp: 1667.67,
    hkd: 168.56,
    jpy: 903.13,
    krw: 1,
    sgd: 975.76,
    thb: 37.53,
    usd: 1322.5,
  },
  {
    id: 75,
    date: '2023-08-30',
    chf: 1505.52,
    cny: 181.34,
    eur: 1438.86,
    gbp: 1672.26,
    hkd: 168.48,
    jpy: 906.69,
    krw: 1,
    sgd: 979.73,
    thb: 37.74,
    usd: 1322.0,
  },
  {
    id: 76,
    date: '2023-08-31',
    chf: 1505.01,
    cny: 181.09,
    eur: 1444.36,
    gbp: 1681.15,
    hkd: 168.41,
    jpy: 905.07,
    krw: 1,
    sgd: 978.96,
    thb: 37.73,
    usd: 1321.4,
  },
  {
    id: 77,
    date: '2023-09-01',
    chf: 1496.83,
    cny: 181.35,
    eur: 1433.77,
    gbp: 1675.68,
    hkd: 168.6,
    jpy: 908.64,
    krw: 1,
    sgd: 978.5,
    thb: 37.76,
    usd: 1322.3,
  },
  {
    id: 78,
    date: '2023-09-04',
    chf: 1489.53,
    cny: 181.77,
    eur: 1421.64,
    gbp: 1660.74,
    hkd: 168.14,
    jpy: 902.94,
    krw: 1,
    sgd: 974.05,
    thb: 37.57,
    usd: 1319.2,
  },
  {
    id: 79,
    date: '2023-09-05',
    chf: 1491.49,
    cny: 181.37,
    eur: 1424.25,
    gbp: 1666.08,
    hkd: 168.39,
    jpy: 900.45,
    krw: 1,
    sgd: 973.65,
    thb: 37.45,
    usd: 1319.3,
  },
  {
    id: 80,
    date: '2023-09-06',
    chf: 1488.98,
    cny: 181.65,
    eur: 1420.44,
    gbp: 1664.65,
    hkd: 168.89,
    jpy: 897.86,
    krw: 1,
    sgd: 973.0,
    thb: 37.35,
    usd: 1324.3,
  },
  {
    id: 81,
    date: '2023-09-07',
    chf: 1494.95,
    cny: 182.04,
    eur: 1429.08,
    gbp: 1665.55,
    hkd: 169.92,
    jpy: 902.63,
    krw: 1,
    sgd: 977.09,
    thb: 37.49,
    usd: 1332.6,
  },
  {
    id: 82,
    date: '2023-09-08',
    chf: 1495.04,
    cny: 182.02,
    eur: 1427.6,
    gbp: 1664.3,
    hkd: 170.26,
    jpy: 906.2,
    krw: 1,
    sgd: 977.19,
    thb: 37.47,
    usd: 1334.7,
  },
  {
    id: 83,
    date: '2023-09-11',
    chf: 1494.01,
    cny: 181.35,
    eur: 1427.94,
    gbp: 1663.68,
    hkd: 170.06,
    jpy: 905.75,
    krw: 1,
    sgd: 976.6,
    thb: 37.47,
    usd: 1333.4,
  },
  {
    id: 84,
    date: '2023-09-12',
    chf: 1495.4,
    cny: 181.82,
    eur: 1432.46,
    gbp: 1667.17,
    hkd: 170.11,
    jpy: 909.08,
    krw: 1,
    sgd: 979.38,
    thb: 37.55,
    usd: 1332.4,
  },
  {
    id: 85,
    date: '2023-09-13',
    chf: 1487.49,
    cny: 181.46,
    eur: 1426.35,
    gbp: 1656.76,
    hkd: 169.42,
    jpy: 901.59,
    krw: 1,
    sgd: 974.61,
    thb: 37.24,
    usd: 1326.1,
  },
  {
    id: 86,
    date: '2023-09-14',
    chf: 1486.07,
    cny: 181.9,
    eur: 1425.06,
    gbp: 1658.42,
    hkd: 169.67,
    jpy: 901.18,
    krw: 1,
    sgd: 975.57,
    thb: 37.14,
    usd: 1327.8,
  },
  {
    id: 87,
    date: '2023-09-15',
    chf: 1480.02,
    cny: 182.26,
    eur: 1410.84,
    gbp: 1645.29,
    hkd: 169.4,
    jpy: 899.08,
    krw: 1,
    sgd: 972.46,
    thb: 37.05,
    usd: 1326.1,
  },
  {
    id: 88,
    date: '2023-09-18',
    chf: 1479.38,
    cny: 182.58,
    eur: 1415.75,
    gbp: 1645.16,
    hkd: 169.62,
    jpy: 897.91,
    krw: 1,
    sgd: 973.78,
    thb: 37.15,
    usd: 1327.6,
  },
  {
    date: '2023-09-19',
    id: 89,
    chf: 1477.77,
    cny: 182.06,
    eur: 1417.76,
    gbp: 1642.25,
    hkd: 169.6,
    jpy: 898.34,
    krw: 1,
    sgd: 972.53,
    thb: 37.17,
    usd: 1326.0,
  },
];
