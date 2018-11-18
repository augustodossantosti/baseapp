This repository includes a base application for other Java/Spring projects that has REST as their Architectural style, Oracle database and user authentication process is performed with a JWT (Json Web Token).

Security:

All Spring Security related configuration is present in the WebAppSecurityConfiguration class.

This app has two authentication filters, one of them dedicated to login proccess and other related to perform authentication via JWT on every request for protected resources. The user information is stored in a database and retrieved during login process.

Login filter handles request to the URL /login (POST) that must present two parameters to this filter: a username and password and gives to the client app a valid JWT after a successful authentication.

JWT filter hadles request for any protected resouces, that must present a valid JWT in the Authotization header.

All resources defined as permitAll and anonymous in WebAppSecurityConfiguration are processed by Anonymous filter provided by Spring.

Model:

As the goal of this project is to be used as basis for other applications there is only one Entity that is exposed via Spring Data Rest.
Some operations are allowed only for authenticated users with USER role and others for users with ADMIN role. This protection is provided by using @PreAuthorize annotation with hasRole value.

Database:

This projects already defined to work with oracle database for dev and prod environment and to work with H2 for tests.
