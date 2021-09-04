# Backend part of application.

## How to start.
0. Start the application.
   ```shell
   docker-compose up -d
   ```
0. Test.
   ```shell
   curl -XGET http://localhost/dishes
   ```
   Should return something like.
   ```json
   {"content":[...],"pageNumber":0,"pageSize":10,"elementNumber":4}
   ```
