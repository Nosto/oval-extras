# OVal-extras

Oval extras is based on [OVal](https://github.com/sebthom/oval) and brings a few more validation constraints on top of it.

## Installation

Oval-Extras artifacts are published to AWS CodeArtifact. Before building, fetch a temporary token and expose it to Gradle (tokens expire after 12 hours):

```
export CODEARTIFACT_AUTH_TOKEN=$(aws codeartifact get-authorization-token \
  --domain <your-domain> \
  --domain-owner <your-domain-owner-account-id> \
  --region us-east-1 \
  --query authorizationToken \
  --output text)
```

Then add the CodeArtifact repository to your `repositories` block:

```
repositories {
    maven {
        url = uri("https://nosto-673366506863.d.codeartifact.us-east-1.amazonaws.com/maven/java/")
        credentials {
            username = "aws"
            password = System.getenv("CODEARTIFACT_AUTH_TOKEN")
        }
    }
}
```

After the repository is configured, declare the Beanie modules you need in `dependencies` as usual.

Copyright (c) 2025 Nosto Solutions