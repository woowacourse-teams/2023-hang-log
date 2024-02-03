import { Text } from 'hang-log-design-system';

import { containerStyling } from '@components/layout/Footer/Footer.style';

const Footer = () => {
  return (
    <footer css={containerStyling}>
      <div>
        문의 : <a href="mailto:hanglog123@gmail.com">hanglog123@gmail.com</a>
      </div>
      <Text>©️ 2023 행록 All rights reserved</Text>
    </footer>
  );
};
export default Footer;
