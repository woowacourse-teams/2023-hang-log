import { ACCESS_TOKEN_KEY } from '@constants/api';

import { accessToken } from '@mocks/data/member';

const TEST_URL = 'http://localhost:3000';

describe('여행 생성 페이지', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.window().then((window) => window.localStorage.setItem(ACCESS_TOKEN_KEY, accessToken));
    cy.visit(`${TEST_URL}/trip-new`);
    cy.wait(400);
  });

  it('검색어를 입력하면 해당하는 도시 목록을 볼 수 있다.', () => {
    cy.get('input[aria-label="방문 도시"]').click().type('오');

    cy.get('ul').contains('오클랜드');
    cy.get('ul').contains('오하이오');
    cy.get('ul').contains('오사카');
    cy.get('ul').contains('오슬로');
  });

  it('검색어에 해당하는 도시가 없다면 대체 텍스트가 보인다.', () => {
    cy.get('input[aria-label="방문 도시"]').click().type('고구마');

    cy.get('ul').should('have.length', 1);
    cy.get('ul').contains('검색어에 해당하는 도시가 없습니다');
  });

  it('도시를 선택하면 목록 창이 닫히면서 도시 뱃지가 추가된고 삭제 버튼을 누르면 삭제된다.', () => {
    cy.get('input[aria-label="방문 도시"]').click().type('오');

    cy.findByText('오클랜드, 뉴질랜드').click();

    cy.get('ul').should('not.exist');
    cy.get('span').contains('오클랜드');

    cy.get('svg[aria-label="삭제 아이콘"]').last().click();
    cy.get('body').should('not.contain.text', '오클랜드');
  });

  it('방문 기간을 클릭해 달력을 열어 방문기간을 입력할 수 있다.', () => {
    cy.get('#date').click();

    const currentMonth = String(new Date().getMonth() + 1).padStart(2, '0');
    const currentYear = String(new Date().getFullYear());

    cy.get(`span[aria-label="${currentYear}년 ${currentMonth}월 1일"]`).click();
    cy.get(`span[aria-label="${currentYear}년 ${currentMonth}월 12일"]`).click();

    cy.get('#date').click();

    cy.get('#date').should(
      'have.value',
      `${currentYear}.${currentMonth}.01 - ${currentYear}.${currentMonth}.12`
    );
  });

  it('도시와 기간이 채워졌을 때만 기록하기 버튼을 누를 수 있다.', () => {
    cy.get('input[aria-label="방문 도시"]').click().type('오');
    cy.findByText('오클랜드, 뉴질랜드').click();

    cy.findByText('기록하기').should('be.disabled');

    cy.get('svg[aria-label="삭제 아이콘"]').last().click();

    cy.get('#date').click();

    const currentMonth = String(new Date().getMonth() + 1).padStart(2, '0');
    const currentYear = String(new Date().getFullYear());

    cy.get(`span[aria-label="${currentYear}년 ${currentMonth}월 1일"]`).click();
    cy.get(`span[aria-label="${currentYear}년 ${currentMonth}월 12일"]`).click();

    cy.get('#date').click();

    cy.findByText('기록하기').should('be.disabled');

    cy.get('input[aria-label="방문 도시"]').click().type('오');
    cy.findByText('오클랜드, 뉴질랜드').click();

    cy.findByText('기록하기').should('be.not.disabled');
  });

  it('기록하기 버튼을 누르면 여행수정 페이지로 이동한다.', () => {
    cy.get('input[aria-label="방문 도시"]').click().type('오');
    cy.findByText('오클랜드, 뉴질랜드').click();

    cy.get('#date').click();

    const currentMonth = String(new Date().getMonth() + 1).padStart(2, '0');
    const currentYear = String(new Date().getFullYear());

    cy.get(`span[aria-label="${currentYear}년 ${currentMonth}월 1일"]`).click();
    cy.get(`span[aria-label="${currentYear}년 ${currentMonth}월 12일"]`).click();

    cy.get('#date').click();

    cy.findByText('기록하기').click();
    cy.url().should('include', 'trip/1/edit');
  });
});
