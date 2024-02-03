export interface CurrencyData {
  id: number;
  date: string;
  usd: number;
  eur: number;
  gbp: number;
  jpy: number;
  cny: number;
  chf: number;
  sgd: number;
  thb: number;
  hkd: number;
  krw: number;
}

export interface CurrencyListData {
  lastPageIndex: number;
  currencies: CurrencyData[];
}
