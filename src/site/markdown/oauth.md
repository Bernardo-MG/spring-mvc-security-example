# OAuth

An external OAuth server may be used instead of the login form.

Currently, the following providers are supported:

* GitHub

## Setting Up GitHub

First of all follow [GitHub's instructions](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/).

Then update the application.properties file, changing the OAuth properties to those values given by GitHub.

Afterward the GitHub login button can be used instead of the form.
