import type { TripsData } from '@type/trips';

import { ACCESS_TOKEN_KEY } from '@constants/api';
import { PATH } from '@constants/path';

import { accessToken } from '@mocks/data/member';

const TEST_URL = 'http://localhost:3000';

describe('로그인', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.visit(TEST_URL);
    cy.wait(400);
  });

  it('웹 사이트 처음 방문 시 서비스 소개 페이지를 볼 수 있다.', () => {
    cy.findByText('로그인');
    cy.findByText('회원가입');
    cy.findByText('시작하기');
  });

  it('로그인 버튼을 클릭하면 로그인 페이지로 이동한다.', () => {
    cy.findByText('로그인').click();
    cy.location('pathname').should('eq', PATH.LOGIN);
  });

  it('회원가입 버튼을 클릭하면 회원가입 페이지로 이동한다.', () => {
    cy.findByText('회원가입').click();
    cy.location('pathname').should('eq', PATH.SIGN_UP);
  });

  it('로그인하면 메인 페이지 화면이 여행 목록 페이지로 변경된다.', () => {
    cy.findByText('로그인').click();

    cy.window().then((window) => window.localStorage.setItem(ACCESS_TOKEN_KEY, accessToken));

    cy.visit(TEST_URL);

    cy.fixture('trips.json').then((expectedData) => {
      expectedData.forEach((item: TripsData) => {
        cy.findByText(item.title).should('be.visible');

        if (item.description) {
          cy.findByText(item.description).should('be.visible');
        }
      });
    });
  });
});
