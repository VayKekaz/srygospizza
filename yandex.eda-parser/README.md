# How to start

```shell
npm run
```

It requires the `src/yandex.eda.html` file. If you want to save custom `eda.yandex.ru` page make sure to load all images
on page before saving (this website hides images when it's not visible on screen).

It will generate `./dishModels.kt` file that will contain all dish models as kotlin `val MOCK_DISHES: List` variable. Copy
and paste it into `/backend/src/main/kotlin/srygos/pizza/backend/mockdata/MockDishes.kt`.
