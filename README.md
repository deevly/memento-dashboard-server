# Dashboard Server

gRPC 기반의 Memento 메인 도메인 서버

Opensearch 내의 사용자 데이터 조회 및 가공, 결과 제공

### Build

1. submodule update를 통해 최신 상태의 proto를 fetch

    ```bash
    $ git submodule update --remote --recursive
    ```

2. OpenSearch 서버 구동
3. Docker build
    ```bash
    $ docker build -t dashboard .
    ```
4. Docker run
    ```bash
    $ docker run --env ACCESS_KEY=${access_key} SECRET_KEY=${secret_key} dashboard:latest
    ```
