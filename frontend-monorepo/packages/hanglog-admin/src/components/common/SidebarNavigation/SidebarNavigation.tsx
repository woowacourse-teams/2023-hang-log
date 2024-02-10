import { Flex, Tab } from 'hang-log-design-system';
import { useLocation, useNavigate } from 'react-router-dom';

import { PATH } from '@constants/path';

import { containerStyling, tabStyling } from './SidevarNavigation.style';

const SidebarNavigation = () => {
  const navigate = useNavigate();
  const location = useLocation().pathname;

  const tabs = [
    { text: '홈', path: PATH.HOME },
    { text: '관리자 계정', path: PATH.ADMIN_MEMBER },
    { text: '도시', path: PATH.CITY },
    { text: '카테고리', path: PATH.CATEGORY },
    { text: '환율', path: PATH.CURRENCY },
  ];

  return (
    <Flex styles={{ justify: 'right' }} css={containerStyling}>
      <Flex styles={{ direction: 'column' }}>
        {tabs.map((item) => (
          <Tab
            key={item.text}
            text={item.text}
            variant="block"
            tabId={item.path}
            selectedId={location}
            changeSelect={() => navigate(item.path)}
            css={tabStyling}
          />
        ))}
      </Flex>
    </Flex>
  );
};

export default SidebarNavigation;
