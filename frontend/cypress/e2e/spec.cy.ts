const TEST_URL = 'http://localhost:3000';

describe('template spec', () => {
  it('passes', () => {
    cy.visit(TEST_URL);
  });
});
