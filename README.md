# Hello CI/CD Starter — annotated learning project

Every important configuration and code section has English comments explaining what it does and why it exists.

## Project purpose

This sample exposes `GET /getHello` and teaches the flow from source code to CI checks and a Docker image. It does not require Azure or AWS.

## Folder map

- `src/main/java/.../HelloCicdApplication.java`: application entry point.
- `src/main/java/.../controller/HelloController.java`: REST endpoint.
- `src/test/java/.../HelloControllerTest.java`: API behavior test.
- `src/test/java/.../architecture/ArchitectureTest.java`: architecture convention test.
- `pom.xml`: dependencies, Java version, build plugins, test coverage and static-analysis configuration.
- `config/checkstyle/checkstyle.xml`: Java style rules.
- `.github/workflows/ci.yml`: automated GitHub Actions jobs.
- `Dockerfile`: multi-stage container build.
- `.dockerignore`: files excluded from Docker's build context.
- `.gitignore`: generated/local files Git should ignore.

## Flow: what happens and why?

1. A developer pushes code or opens a pull request.
2. GitHub Actions checks out the repository on an Ubuntu runner.
3. The workflow installs Java 17 and caches Maven dependencies.
4. `mvn clean test` removes old Maven output, compiles source/tests and runs JUnit, MockMvc and ArchUnit tests.
5. `mvn verify` runs later lifecycle checks, including JaCoCo coverage, PMD and Checkstyle.
6. A separate job runs OWASP Dependency-Check and uploads its report.
7. When CI is green, a later learning step will build and scan a Docker image and optionally publish it to GHCR.

## Run locally

```bash
mvn clean test
mvn verify
mvn spring-boot:run
```

Expected response at `http://localhost:8080/getHello`:

```text
Hello from Spring Boot!
```

Build and run the JAR:

```bash
mvn clean package
java -jar target/hello-cicd-0.0.1-SNAPSHOT.jar
```

## Understand the tools

- **JUnit 5 / Spring Test / MockMvc**: check the API route, status and response body without opening a real network port.
- **ArchUnit**: checks architectural conventions against compiled classes.
- **JaCoCo**: measures which lines tests execute and writes `target/site/jacoco/index.html`. This starter sets a learning threshold of 50%; production thresholds should be based on team standards.
- **PMD**: identifies selected potential defects and maintainability problems.
- **Checkstyle**: enforces the rules in `config/checkstyle/checkstyle.xml`.
- **OWASP Dependency-Check**: matches third-party dependencies to known vulnerability data. The first run can take time or be rate-limited by the external data feed; distinguish feed/network errors from actual vulnerability findings.
- **SonarQube**: the Maven scanner is configured, but no server is automatically started. Run a local SonarQube Community Build and supply a local token only when performing the Sonar step.
- **Docker**: first stage builds the JAR; second stage contains only the Java runtime and JAR and runs as a non-root user.

## Push to GitHub

Create an empty repository on GitHub. From this project folder run:

```bash
git init
git add .
git commit -m "Add annotated Spring Boot CI/CD starter"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPOSITORY.git
git push -u origin main
```

Replace the remote URL with your repository's real URL. Never commit access tokens, passwords, private keys or `.env` files.

Open the repository's **Actions** tab to see the workflow run. It starts on pushes to `main`, pull requests into `main`, or a manual dispatch.

## Docker commands

```bash
docker build -t hello-cicd:local .
docker run --rm -p 8080:8080 hello-cicd:local
```

Visit `http://localhost:8080/getHello`. Stop the running container with `Ctrl+C`.

## OWASP scan locally

```bash
mvn org.owasp:dependency-check-maven:12.1.0:check
```

Look for `target/dependency-check-report.html`. The configured initial failure threshold is CVSS 7.0; a team should review findings and decide a suitable security policy.

## SonarQube local scan (next stage)

Start a local SonarQube Community Build using its current official Docker instructions. Then run this from the project directory:

```bash
mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=YOUR_LOCAL_SONAR_TOKEN
```

Generate the token in your local SonarQube UI. Do not write the token into `pom.xml`, GitHub source files or committed configuration. Keep the local server private; do not expose it publicly.

## Important limitations

- This is a learning starter, not a complete production security policy.
- The workflow uses action major-version tags for readability; production workflows should pin reviewed actions to full commit SHAs.
- GitHub-hosted runner usage and private repository quotas depend on the account/plan.
- The current workflow does not yet build or publish Docker images; that is a planned next stage after the basic CI is understood.
