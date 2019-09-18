#### ДЗ-16: MessageServer

Задачи:
- Cервер из предыдущего ДЗ про MessageSystem разделить на три приложения:
  - MessageServer
  - Frontend
  - DBServer
- Запускать Frontend и DBServer из MessageServer
- Сделать MessageServer сокет-сервером, Frontend и DBServer клиентами
- Пересылать сообщения с Frontend на DBService через MessageServer
- Запустить приложение с двумя серверами фронтенд и двумя серверами баз данных на разных портах.
Если у вас запуск веб приложения в контейнере, то MessageServer может копировать root.war в контейнеры при старте

Как запустить:
```
1. запустить БД hw16-multiprocess/backend/h2/bin/h2.bat
2. запустить ru.otus.hw16.server.MainServer
3. в браузере установить соединение с фронтами по портам 8090, 8091. Фронты работают с одной БД
```