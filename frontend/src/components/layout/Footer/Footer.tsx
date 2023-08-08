import { Text } from 'hang-log-design-system';

import { containerStyling } from '@components/layout/Footer/Footer.style';

const Footer = () => {
  return (
    <footer css={containerStyling}>
      <a href="mailto:hanglog123@gmail.com">문의</a>
      <Text>©️ 2023 행록 All rights reserved</Text>
    </footer>
  );
};
export default Footer;
