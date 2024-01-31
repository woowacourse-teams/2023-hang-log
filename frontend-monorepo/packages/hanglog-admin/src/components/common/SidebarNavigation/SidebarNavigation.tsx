import { Tab, Flex } from 'hang-log-design-system';
import { useLocation, useNavigate } from 'react-router-dom';

import { PATH } from '@constants/path';
import { containerStyling, tabStyling } from './SidevarNavigation.style';

const SidebarNavigation = () => {
  const navigate = useNavigate();
  const location = useLocation().pathname;

  return (
    <Flex styles={{ justify: 'right' }} css={containerStyling}>
      <Flex styles={{ direction: 'column' }}>
        <Tab
          text="홈"
          variant="block"
          tabId={PATH.HOME}
          selectedId={location}
          changeSelect={() => navigate(PATH.HOME)}
          css={tabStyling}
        />
        <Tab
          text="관리자"
          variant="block"
          tabId={PATH.ADMIN_MEMBER}
          selectedId={location}
          changeSelect={() => navigate(PATH.ADMIN_MEMBER)}
          css={tabStyling}
        />
        <Tab
          text="도시"
          variant="block"
          tabId={PATH.CITY}
          selectedId={location}
          changeSelect={() => navigate(PATH.CITY)}
          css={tabStyling}
        />
        <Tab
          text="카테고리"
          variant="block"
          tabId={PATH.CATEGORY}
          selectedId={location}
          changeSelect={() => navigate(PATH.CATEGORY)}
          css={tabStyling}
        />
        <Tab
          text="환율"
          variant="block"
          tabId={PATH.CURRENCY}
          selectedId={location}
          changeSelect={() => navigate(PATH.CURRENCY)}
          css={tabStyling}
        />
      </Flex>
    </Flex>
  );
};

export default SidebarNavigation;
