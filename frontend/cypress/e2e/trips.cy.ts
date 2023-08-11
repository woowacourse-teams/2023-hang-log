import { ACCESS_TOKEN_KEY } from '@constants/api';
import { accessToken } from '@mocks/data/member';
import type { CityData } from '@type/city';
import type { TripsData } from '@type/trips';

const TEST_URL = 'http://localhost:3000';

describe('여행 목록 페이지', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.window().then((window) => window.localStorage.setItem(ACCESS_TOKEN_KEY, accessToken));
    cy.visit(TEST_URL);
    cy.wait(3000);
  });

  it('여행 목록 페이지에 처음 방문 시 여행 목록 데이터가 렌더링되기 전에 skeleton을 볼 수 있다.', () => {
    cy.clock();

    cy.visit(`${TEST_URL}/trip/1/edit`);

    cy.tick(3000);

    cy.get('.skeleton').should('be.visible');
  });

  it('여행 목록 페이지에서 여행에 대한 정보를 볼 수 있다.', () => {
    cy.fixture('trips.json').then((expectedData) => {
      expectedData.forEach((item: TripsData) => {
        cy.findByText(item.title).should('be.visible');

        if (item.description) {
          cy.findByText(item.description).should('be.visible');
        }
      });
    });
  });

  it('여행 목록 페이지에서 여행 목록을 누르면 데이터가 렌더링되기 전에 skeleton을 볼 수 있다.', () => {
    cy.fixture('trips.json').then((expectedData) => {
      cy.findByText(expectedData[0].title).click();

      cy.get('.skeleton').should('be.visible');
    });
  });

  it('여행 목록 페이지에서 여행 목록을 누르면 여행에 대한 정보를 볼 수 있다.', () => {
    cy.fixture('trips.json').then((expectedData) => {
      cy.findByText(expectedData[0].title).click();
    });
    cy.fixture('trip.json').then((expectedData) => {
      cy.findByText(expectedData.title).should('be.visible');

      expectedData.cities.forEach((city: CityData) => {
        cy.findByText(city.name);
      });
    });
  });
});
