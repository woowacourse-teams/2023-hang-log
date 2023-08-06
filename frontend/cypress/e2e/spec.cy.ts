import { PROD } from '@constants/api';

const TEST_URL = PROD
  ? `${window.location.protocol}//${process.env.PROD_BASE_URL}`
  : 'localhost:3000';

describe('template spec', () => {
  it('passes', () => {
    cy.visit(TEST_URL);
  });
});
