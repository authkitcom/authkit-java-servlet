# AuthKit Java Servlet Integration

Client integration library for OpenID based JWT authentication.

Features include:

* Loading up OpenID configuration from an issuer.
* Validating an incoming JWT.
* Creating a default principal and storing in a request wrapper.
* Custom translation of a principal for additional logic.

Items still needing to be done:

* Setup and usage documentation.
* Caching of /userinfo (??). Right now /userinfo is hit on each and every
  request.

