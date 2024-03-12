export const environment = {
    production: false,
    serverUrl: '/api',
    keycloak: {
      // Url of the Identity Provider
      issuer: 'http://localhost:8200/',
      // Realm
      realm: 'uvew',
      clientId: 'uvew_ui',
    },
  };