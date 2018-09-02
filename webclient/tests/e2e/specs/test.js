describe('Sanity tests', () => {

  it('should be able to use the top navigation', () => {
    cy.visit('/');
    cy.contains('h1', 'ARGENT repartit gratuitement et efficacement notre thune');

    cy.contains('Create group').click();
    cy.contains('h1', 'Create a group');

    cy.contains('ARGENT').click();
    cy.contains('h1', 'ARGENT repartit gratuitement et efficacement notre thune');
  });

  it('Should be able to create a group and add a member', () => {
    const randomNumber = Math.floor((1 + Math.random()) * 0x10000).toString(16);
    const groupName = 'test group' + randomNumber;

    cy.visit('/');

    cy.contains('Create a group').click();
    cy.get('.input').type(groupName);
    cy.contains('.button', 'Create group').click();
    cy.contains('h1', groupName);
    cy.contains('Add member').click();
    cy.get('.input').type('a member');
    cy.get('.is-primary').click();
    cy.contains('a member');
    cy.contains('My groups').click();
    cy.contains(groupName).click();
    cy.contains('h1', groupName);
  });
});
