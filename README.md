# Srygospizza

Is my pet project to show off what I know and able to do. This is a simple CRUD application with frontend written in
React.js and backend written in Spring and Kotlin.
<br />
Preview (click to watch a 1min-long video):<br />
[![preview](https://img.youtube.com/vi/ItjP3YtCbGI/0.jpg)](https://www.youtube.com/watch?v=ItjP3YtCbGI)

# Dependencies

Just to start and test how it works

- Docker

For development

- Docker
- Java 11+
- Kotlin SDK
- Yarn
- Node.js

# How to start

### Whole application

```shell
docker-compose up --build -d
```

`--build` points docker to build containers. Optional. <br />
`-d` flag points to run containers in background, not in shell. <br />

After start go to [`http://localhost:80/`](http://localhost:80/) and see if it works.

### To start only backend or only frontend use

```shell
docker-compose up --no-deps <serviceName>
```

`--no-deps` tells docker not to start services under `depends-on` property in `docker-compose.yml`. <br />
`<serviceName>` is name of service to start. Must be `frontend` or `backend`. <br />

Example:

```shell
docker-compose up --no-deps frontend
```

will start only frontend. Backend will stay untouched.

### About `.env` file

After some changes it's required to rebuild containers. Watch after comments near these variables.
