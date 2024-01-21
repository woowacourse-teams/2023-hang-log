import '@testing-library/cypress/add-commands';
import type { RecommendedTripsData, TripsData } from '@type/trips';

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

  it('웹 사이트 처음 방문 시 커뮤니티 페이지를 볼 수 있다.', () => {
    cy.findByText('로그인');
  });

  it('로그인 버튼을 클릭하면 로그인 페이지로 이동한다.', () => {
    cy.findByText('로그인').click();
    cy.location('pathname').should('eq', PATH.LOGIN);
  });

  it('로그인하면 커뮤니티 페이지로 변경된다.', () => {
    cy.findByText('로그인').click();
    cy.findByText('카카오로 계속하기').click();

    cy.visit(TEST_URL);

    cy.window().then((window) => window.localStorage.setItem(ACCESS_TOKEN_KEY, accessToken));

    cy.fixture('recommendedTrips.json').then((expectedData: RecommendedTripsData) => {
      cy.findByText(expectedData.title).should('be.visible');

      expectedData.trips.forEach((item: TripsData) => {
        cy.findByText(item.title).should('be.visible');

        if (item.description) {
          cy.findByText(item.description).should('be.visible');
        }
      });
    });
    cy.findByText('커뮤니티').should('be.visible');
  });
});
