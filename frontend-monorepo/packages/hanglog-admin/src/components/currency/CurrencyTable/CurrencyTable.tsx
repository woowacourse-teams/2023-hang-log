import type { CurrencyData } from '@/types/currency';

import { tableStyling } from './CurrencyTable.style';

interface CurrencyTableProps {
  currencies: CurrencyData[];
}

const CurrencyTable = ({ currencies }: CurrencyTableProps) => {
  return (
    <table css={tableStyling}>
      <thead>
        <tr>
          <th>ID</th>
          <th>날짜</th>
          <th>USD</th>
          <th>EUR</th>
          <th>GBP</th>
          <th>JPY(100)</th>
          <th>CNY</th>
          <th>CHF</th>
          <th>SGD</th>
          <th>THB</th>
          <th>HKD</th>
          <th> </th>
        </tr>
      </thead>
      <tbody>
        {currencies.map((currency) => (
          <tr key={currency.id}>
            <td>{currency.id}</td>
            <td>{currency.date}</td>
            <td>{currency.usd}</td>
            <td>{currency.eur}</td>
            <td>{currency.gbp}</td>
            <td>{currency.jpy}</td>
            <td>{currency.cny}</td>
            <td>{currency.chf}</td>
            <td>{currency.sgd}</td>
            <td>{currency.thb}</td>
            <td>{currency.hkd}</td>
            <td></td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default CurrencyTable;
