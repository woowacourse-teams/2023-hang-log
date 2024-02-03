import '@testing-library/cypress/add-commands';
import type { TripsData } from '@type/trips';

import { ACCESS_TOKEN_KEY } from '@constants/api';

import { accessToken } from '@mocks/data/member';

const TEST_URL = 'http://localhost:3000';

describe('커뮤니티 목록 페이지', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.window().then((window) => window.localStorage.setItem(ACCESS_TOKEN_KEY, accessToken));
    cy.visit(TEST_URL);
    cy.wait(3000);
  });

  it('여행 목록 페이지에 처음 방문 시 여행 목록 데이터가 렌더링되기 전에 skeleton을 볼 수 있다.', () => {
    cy.visit(`http://localhost:3000/trip/1/edit`);

    cy.get('.skeleton').should('be.visible');
  });

  it('커뮤니티 페이지에서 여행에 대한 정보를 볼 수 있다.', () => {
    cy.fixture('communityTrips.json').then((expectedData) => {
      expectedData.forEach((item: TripsData) => {
        cy.findByText(item.title).should('be.visible');

        if (item.description) {
          cy.findByText(item.description).should('be.visible');
        }
      });
    });
  });

  it('커뮤니티 페이지에서 목록을 이동할 수 있다.', () => {
    cy.findByText('2').click();

    cy.fixture('communityTrips.json').then((expectedData) => {
      expectedData.forEach((item: TripsData) => {
        cy.findByText(item.title).should('be.visible');

        if (item.description) {
          cy.findByText(item.description).should('be.visible');
        }
      });
    });
  });
});
