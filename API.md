## Алгоритм взаимодействия клиента с сервером
___
**Перед написанием логики обработки сообщений на клиенте, клиенту следует точь в точь реализовать классы**
- [MessageType](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/websocket/MessageType.java)
- [SocketMessage](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/websocket/SocketMessage.java)
- [MessageContent](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/websocket/MessageContent.java)
- [Player](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/Player.java)
- [Room](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/Room.java)
- [GameState](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/GameState.java)
- [Card](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/Card.java)
- [CardSuit](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/CardSuit.java)
- [CardNumber](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/CardNumber.java)

___
После авторизации пользователя и подключения к веб-сокету, общение между клиентом и сервером идет посредством `JSON-сообщений`. Все `JSON-сообщения` представляют собой сериализованные объекты класса [SocketMessage](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/websocket/SocketMessage.java). В этом классе нужно обратить внимание на поле с типом [MessageContent](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/websocket/MessageContent.java). В этом классе находятся конструкторы, которые помогут понять, как следуюет десериализовывать все входящие сообщения.

**1. Заход в комнату**
   1. Чтобы зайти в комнату, клиент нажимает кнопку `Найти игру` в Главном меню, затем появляется диалоговое окно в котором нужно ввести код комнаты, в которую хотим войти. После ввода кода комнаты нажимаем `Ок` в диалоговом окне. После нажатии кнопки `Ок` клиент посылает на сервер сообщение с типом `MessageType.PLAYER_ROOM_JOIN`. Это сообщение содержит код комнаты, к которой клиент присоединяется и логин клинета, который присоединяется к комнате

        ```
        {
            "messageType": "PLAYER_ROOM_JOIN",
            "content":
                {
                    "roomCode": 1,
                    "userLogin": "bh"
                },
            "author": "bh",
            "datetime": "2023-01-20T13:25:56.364",
            "receiver": "ws://192.168.1.116:8080/room/websocket"
        }
        ```

   2. После отправки сообщения клиентом, сервер принимает присланное сообщение, добавляет игрока к себе в комнату и отсылает всем клиентам ответное сообщение с типом `MessageType.PLAYER_ROOM_JOIN`
        ```
        {
            "messageType":"PLAYER_ROOM_JOIN",
            "content":
                {
                    "roomCode":1,
                    "userLogin":"bh",
                    "userName":"Nik Sher",
                    "moneyValue":4576,
                    "cardSuit":null,
                    "cardNumber":null,
                    "roomPlayersList":null
                },
            "author":"ws://192.168.1.116:8080/room/websocket",
            "dateTime":"2023-01-20T13:25:58.0494656",
            "receiver":"bh"
        }
        ```
        Это сообщение содержит информацию о присоединившемся пользователе, которая нужна для обновления пользовательского интерфейса
        - `userLogin` - логин пользователя
        - `userName` - имя пользователя
        - `moneyValue` - все текущие деньги пользователя

   3. После отправки ответного сообщения сервером, клиент получает это сообщение и
        - если `userLogin` совпадает с логином текущего клиента, то текущий клиент игнорирует присланное сообщение
        - если `userLogin` не совпадает с логином текущего клиента (т.е. текущий клиент уже сидит в комнате и в комнату присоединяется новый клиент), то текущий клиент обновляет пользовательский интерфейс (добавляет новый клиент на следующее свободное место) с использованием информации из полученного сообщения
   4. После игнорирования/добавления пользователя клиент отправляет сообщение серверу c типом `MessageType.CHECK_ROOM_PLAYERS`. Этим сообщением клиент запрашивает информацию о всех других игроках, уже сидящих в комнате.
        ```
        {
            "messageType":"CHECK_ROOM_PLAYERS",
            "content":
                {
                    "roomCode": 1
                },
            "author":"bh",
            "datetime":"2023-01-20T13:25:56.567",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
   5. Сервер принимает это сообщение и формирует ответное сообщение c типом `MessageType.CHECK_ROOM_PLAYERS`. Это сообщение содержит информацию о всех игроках в текущей комнате
        ```
        {
            "messageType":"CHECK_ROOM_PLAYERS",
            "content":
                {
                    "roomCode":1,
                    "userLogin":null,
                    "userName":null,
                    "moneyValue":0,
                    "cardSuit":null,"cardNumber":null,"roomPlayersList": [
                        {
                            "login":"a",
                            "name":"Sh Sh",
                            "currentStake":0,
                            "cash":4534,
                            "card1":null,
                            "card2":null,
                            "smallBlind":false,
                            "bigBlind":false,
                            "ready":true
                        },
                        {
                            "login":"b",
                            "name":"Sha Sha",
                            "currentStake":0,
                            "cash":4134,
                            "card1":null,
                            "card2":null,
                            "smallBlind":false,
                            "bigBlind":false,
                            "ready":true
                        },
                        {
                            "login":"bh",
                            "name":"Nik Sher",
                            "currentStake":0,
                            "cash":4576,
                            "card1":null,
                            "card2":null,
                            "smallBlind":false,
                            "bigBlind":false,
                            "ready":false
                        }
                    ]
                },
            "author":"ws://192.168.1.116:8080/room/websocket",
            "dateTime":"2023-01-20T13:25:58.1364657",
            "receiver":"bh"
        }
        ```
        В этом сообщении нужно воспринимать ТОЛЬКО поля `roomCode` и `roomPlayersList`. На клиенте нужно сформировать список из игроков в комнате с использованием данных из массива `roomPlayersList`. Каждый элемент массива представляет собой сериализованный объект класса [Player](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/Player.java)
        
        Затем нужно пройтись по всем элементам сформированного списка и обновить пользовательский интерфейс в соответсвии с полученной информацией:
        - если `login` совпадает с логином текущего клиента, то текущий клиент игнорирует текущий элемент списка
        - если `login` не совпадает с логином текущего клиента, то текущий клиент обновляет пользовательский интерфейс (добавляет новый клиент на следующее свободное место) с использованием информации из текущего элемента списка
   6. Ура. Мы присеодинились к комнате. Переходим к следующему этапу

**2. Готовность игроков**
  1. После того, как клиент вошёл в комнату и в пользовательском интрерфейсе появилась вся информация об игроке клиента и обо всех других игроках в комнате, у клиента должна появится кнопка с надписью `Готов`. После нажатия этой кнопки, на её месте появляется новая кнопка с надписью `Не готов` и на клиент отправляет на сервер сообщение с типом `MessageType.PLAYER_READY_SET` с информацией о том, что клиент готов
        ```
        {
            "messageType":"PLAYER_READY_SET",
            "content":
                {
                    "roomCode": 1
                },
            "author": "bh",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  2. Сервер получает присланное сообщение, ищет в списке игроков игрока по логину, с ипользованием значения из поля `author` и меняет поле `Player.isReady` с `false` на `true`
  3. Затем сервер проходит по всему списку игроков в комнате и проверяет статус их готовности
       - если не все игроки готовы, то сервер отправляет ответное сообщение всем клиентам с типом `MessageType.OK`. Клиентам, получившим это сообщение, следует игнорировать это сообщение.
            ```
            {
                "messageType":"OK",
                "content":
                    {
                        "roomCode": 1,
                        "userLogin":null,
                        "userName":null,
                        "moneyValue":0,
                        "cardSuit":null,
                        "cardNumber":null,"roomPlayersList":null
                    },
                "author":"ws://192.168.1.116:8080/room/
                websocket","dateTime":"2023-01-20T14:31:32.0457535",
                "receiver":"bh"
            }
            ```
       - если все игроки готовы, то сервер отправялет всем клиентам сообщение с типом `MessageType.ROUND_BEGIN`
            ```
            {
                "messageType":"ROUND_BEGIN",
                "content":
                    {   "roomCode":1,
                        "userLogin":null,
                        "userName":null,
                        "moneyValue": 0,
                        "cardSuit":null,
                        "cardNumber":null,
                        "roomPlayersList":null
                    },
                "author":"ws://192.168.1.116:8080/room/websocket",
                "dateTime":"2023-01-20T14:36:43.8181262",
                "receiver":"bh"
            }
            ```
  4. Клиент получает присланное сообщение и меняет этап в комнате на `GameState.Blinds`
  5. Переходим на следующий этап

**3. Первый этап игры: Блайнды**
  1. Клиент отправляет на сервер сообщение с типом `MessageType.WHO_IS_SMALL_BLIND`
        ```
        {
            "messageType":"WHO_IS_SMALL_BLIND",
            "content":
                {   "roomCode":1,
                    "userLogin":null,
                    "userName":null,
                    "moneyValue": 0,
                    "cardSuit":null,
                    "cardNumber":null,
                    "roomPlayersList":null
                },
            "author":"bh",
            "dateTime":"2023-01-20T14:36:43.8181262",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  2. Сервер принимает присланное сообщение, проверяет список игроков в команте, определяет игрока, который в этой игре должен поставить малый блайнд, и отправляет всем клиентам сообщение с типом `MessageType.WHO_IS_SMALL_BLIND`
        ```
            {
                "messageType": "WHO_IS_SMALL_BLIND",
                "content":
                    {
                        "roomCode": 1,
                        "userLogin": "bh"
                    },
                "author": "ws://192.168.1.116:8080/room/websocket",
                "datetime": "2023-01-20T13:25:56.364",
                "receiver": "bh"
            }
        ``` 
  3. Клиент получает сообщение и 
        - если `userLogin` не совпадает с логином текущего клиента то клиент игнорирует сообщение
        - если `userLogin` совпадает с логином текущего клиента то на пользовательском интерфейсе клиента появляется надпись `Поставьте малый блайнд` и активируется кнопка `BET`. Принажатии на эту кнопку, на клиенте появляется диалоговое окно с вводом суммы для ставки. При нажатии на кнопку `OK` в этом диалоговом окне в пользовательском интерфейсе клиента рядом с местом клиента появляется размер ставки, которую он поставил и увеличивает значение поля `Player.currentStake`. Затем клиент отправялет серверу сообщение с типом `MessageType.PLAYER_MAKE_BET`
            ```
            {
                "messageType": "PLAYER_MAKE_BET",
                "content":
                    {
                        "roomCode": 1,
                        "userLogin": "bh",
                        "moneyValue: 10
                    },
                "author": "bh",
                "datetime": "2023-01-20T13:25:56.364",
                "receiver": "ws://192.168.1.116:8080/room/websocket"
            }
            ``` 
  4. Сервер получает присланное сообщение, увеличивает текущую ставку игрока (увеличивает значение поля `Player.currentStake`) и отправляет всем клиентам сообщение с типом `MessageType.PLAYER_MAKE_BET`
        ```
        {
            "messageType":"PLAYER_MAKE_BET",
            "content":
                {
                    "roomCode": 1,
                    "userLogin": "bh",
                    "moneyValue: 10
                },
            "author": "ws://192.168.1.116:8080/room/websocket",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"bh"
        }
        ```
  5. Клиент получает отправленное сервером сообщение и
        - если `userLogin` совпадает с логином текущего клиента то клиент игнорирует сообщение
        - если `userLogin` не совпадает с логином текущего клиента то клиент ищет в списке игроков в комнате игрока по пришедшему логину и обновляет его поле `Player.currentStake`, и также обновляет пользовательский интерфейс - рядом с местом игрока должно обновится значение его текущей ставки
  6. Клиент отправляет на сервер сообщение с типом `MessageType.WHO_IS_BIG_BLIND`
        ```
            {
                "messageType":"WHO_IS_BIG_BLIND",
                "content":
                    {   "roomCode":1,
                        "userLogin":null,
                        "userName":null,
                        "moneyValue": 0,
                        "cardSuit":null,
                        "cardNumber":null,
                        "roomPlayersList":null
                    },
                "author":"bh",
                "dateTime":"2023-01-20T14:36:43.8181262",
                "receiver":"ws://192.168.1.116:8080/room/websocket"
            }
        ```
  7. Сервер принимает присланное сообщение и проверяет список игроков в команте, определяет игрока, который в этой игре должен поставить большой блайнд, и отправляет всем клиентам ответное сообщение с типом `MessageType.WHO_IS_BIG_BLIND`
        ```
            {
                "messageType": "WHO_IS_BIG_BLIND",
                "content":
                    {
                        "roomCode": 1,
                        "userLogin": "bh"
                    },
                "author": "ws://192.168.1.116:8080/room/websocket",
                "datetime": "2023-01-20T13:25:56.364",
                "receiver": "bh"
            }
         ``` 
  8. Клиент получает сообщение и
      - если `userLogin` не совпадает с логином текущего клиента то клиент игнорирует сообщение
      - если `userLogin` совпадает с логином текущего клиента то на пользовательском интерфейсе клиента появляется надпись `Поставьте большой блайнд` и активируется кнопка `BET`. Принажатии на эту кнопку, на клиенте появляется диалоговое окно с вводом суммы для ставки. При нажатии на кнопку `OK` в этом диалоговом окне в пользовательском интерфейсе клиента рядом с местом клиента появляется размер ставки, которую он поставил и увеличивает значение поля `Player.currentStake`. Затем клиент отправялет серверу сообщение с типом `MessageType.PLAYER_MAKE_BET`
        ```
            {
                "messageType": "PLAYER_MAKE_BET",
                "content":
                    {
                        "roomCode": 1,
                        "userLogin": "bh",
                        "moneyValue: 20
                    },
                "author": "bh",
                "datetime": "2023-01-20T13:25:56.364",
                "receiver": "ws://192.168.1.116:8080/room/websocket"
            }
        ``` 
  9. Сервер получает присланное сообщение, увеличивает текущую ставку игрока (увеличивает значение поля `Player.currentStake`) и отправляет всем клиентам сообщение с типом `MessageType.PLAYER_MAKE_BET`
        ```
        {
            "messageType":"PLAYER_MAKE_BET",
            "content":
                {
                    "roomCode": 1,
                    "userLogin": "bh",
                    "moneyValue: 10
                },
            "author": "ws://192.168.1.116:8080/room/websocket",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"bh"
        }
        ```
  10. Клиент получает отправленное сервером сообщение и
        - если `userLogin` совпадает с логином текущего клиента то клиент игнорирует сообщение
        - если `userLogin` не совпадает с логином текущего клиента то клиент ищет в списке игроков в комнате игрока по пришедшему логину и обновляет его поле `Player.currentStake`, и также обновляет пользовательский интерфейс - рядом с местом игрока должно обновится значение его текущей ставки
  11. Затем клиент отправляет серверу сообщение с типом `MessageType.IS_NEXT_STEP_OF_ROUND`
        ```
        {
            "messageType":"IS_NEXT_STEP_OF_ROUND",
            "content":
                {
                    "roomCode": 1
                },
            "author": "bh",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  12. Сервер принимает отправленное клиентом сообщение, переводит комнату в следующий этап игры - изменяет этап на `GameState.PREFLOP`. Затем сервер отправляет всем клиентам сообщение с типом `MessageType.NEXT_STEP_OF_ROUND`
        ```
        {
            "messageType":"NEXT_STEP_OF_ROUND",
            "content":
                {
                    "roomCode": 1
                },
            "author": "ws://192.168.1.116:8080/room/websocket",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"bh"
        }
        ```
  13. Клиент получает отправленное сообщение и изменяет этап игры в комнате на `GameState.PREFLOP`

        > Тут стоит **ОБЯЗАТЕЛЬНО УЧЕСТЬ** один момент: на сервер приходит сообщение с типом `MessageType.IS_NEXT_STEP_OF_ROUND` от ВСЕХ клиентов. Т.е. сервер отошлет сообщение c типом `MessageType.NEXT_STEP_OF_ROUND` НЕСКОЛЬКО раз. Задача клиента на этоп этапе - обработать **ТОЛЬКО ПЕРВОЕ** пришедшее сообщение с типом `MessageType.NEXT_STEP_OF_ROUND`. Все следующие сообщения с типом `MessageType.NEXT_STEP_OF_ROUND` следует игнорировать.

  14. Переходим на следующий этап игры.

**4. Второй этап игры: Префлоп**

  1. Клиент отправляет сообщения для получения двух карт для каждого игрока в комнате. Для каждой карты клиент отправляет сообщение с типом `MessageType.DrawCard`. Всего клиент отправляет `2 * <количество игроков в комнате>` запросов
        ```
        {
            "messageType":"DRAW_CARD",
            "content":
                {
                    "roomCode": 1,
                    "userLogin":"bh"
                },
            "author":"bh",
            "datetime":"2023-01-20T20:23:36.724",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
        В поле `userLogin` нужно помещать логин игрока для которого запрашивается карта.
  2. Сервер получает отправленные сообщения, выбирает случайные карты из колоды в комнате, удаляет выбранные карты из колоды в комнате и на каждое полученное сообщение отправляет всем клиентам сообщения с типом `MessageType.DrawCard`. В этих сообщениях содержаться сериализованные поля класса [Card](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/Card.java) - [CardSuit](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/CardSuit.java) (масть карты) и [CardNumber](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/CardNumber.java) (номер карты)
        ```
        {
            "messageType":"DRAW_CARD",
            "content":
                {
                    "roomCode":1,
                    "userLogin":"bh",
                    "userName":null,
                    "moneyValue":0,
                    "cardSuit":"SPADES",
                    "cardNumber":"TEN",
                    "roomPlayersList":null
                },
            "author":"ws://192.168.1.116:8080/room/websocket",
            "dateTime":"2023-01-20T20:23:37.5796895",
            "receiver":"bh"
        }
        ``` 
  3. Клиент получает все отправленные сообщения с информацией о картах и обновляет пользовательский интерфейс в соответствии с полученной информацией о картах. В каждом сообщении клиенту нужно проверять поле `userLogin`, `cardSuit` и `cardNumber`, и обновлять картинки карт. В руке у игрока, за которого играет текущий клиент, должны появится картинки выданных карт. В руках у других игроков в комнате должны появится изображения рубашек карт (карты других игроков не должны быть видны)
  4. Клиент отправляет серверу сообщение с типом `MessageType.PLAYER_MUST_MAKE_BET`, чтобы узнать, кто следующий должен сделать ставку. В поле `moneyValue` нужно вставить минимальную ставку, которую должен сделать игрок
        ```
        {
            "messageType":"PLAYER_MUST_MAKE_BET",
            "content":
                {
                    "roomCode": 1
                    "moneyValue": 20
                },
            "author":"bh",
            "dateTime":"2023-01-20T20:23:37.5796895",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  5. Сервер получает отправленное клиентом сообщение, проверяет список игроков в комнате и находит первого игрока, у которого текущая ставка меньше `moneyValue` и отправляет всем клиентам сообщение с типом `MessageType.PLAYER_MUST_MAKE_BET`
        ```
        {
            "messageType":"PLAYER_MUST_MAKE_BET",
            "content":
                {
                    "roomCode": 1
                    "userLogin": "bh"
                    "moneyValue": 20
                },
            "author":"ws://192.168.1.116:8080/room/websocket",
            "dateTime":"2023-01-20T20:23:37.5796895",
            "receiver":"bh"
        }
        ```
  6. Клиент получает отправленное сервером сообщение и
      - если `userLogin` совпадает с логином текущего клиента, то на пользовательском интерфейсе клиента появляется надпись `Сделайте ставку (минимум: moneyValue)` и активируется кнопка `BET`. Принажатии на эту кнопку, на клиенте появляется диалоговое окно с вводом суммы для ставки. При нажатии на кнопку `OK` в этом диалоговом окне в пользовательском интерфейсе клиента рядом с местом клиента появляется размер ставки, которую он поставил и увеличивает значение поля `Player.currentStake`. Затем клиент отправялет серверу сообщение с типом `MessageType.PLAYER_MAKE_BET`
  7. Сервер получает присланное сообщение, увеличивает текущую ставку игрока (увеличивает значение поля `Player.currentStake`) и отправляет всем клиентам сообщение с типом `MessageType.PLAYER_MAKE_BET`
        ```
        {
            "messageType":"PLAYER_MAKE_BET",
            "content":
                {
                    "roomCode": 1,
                    "userLogin": "bh",
                    "moneyValue: 10
                },
            "author": "ws://192.168.1.116:8080/room/websocket",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"bh"
        }
        ```
  8. Клиент получает отправленное сервером сообщение и
        - если `userLogin` совпадает с логином текущего клиента то клиент игнорирует сообщение
        - если `userLogin` не совпадает с логином текущего клиента то клиент ищет в списке игроков в комнате игрока по пришедшему логину и обновляет его поле `Player.currentStake`, и также обновляет пользовательский интерфейс - рядом с местом игрока должно обновится значение его текущей ставки
  9. Далее повторяются шаги с 4 по 8, пока ставки всех игроков не уравняются.
  10. Когда сервер в очередной раз будет проверять ставки игроков и в этот раз все поля `Player.currentStake` будут равны, сервер переводит комнату в следующий этап игры - изменяет этап на `GameState.FLOP`. Затем сервер отправляет всем клиентам сообщение с типом `MessageType.NEXT_STEP_OF_ROUND`. При этом, в поле `MessageContent.moneyValue` клиент помещает текущее значение банка комнаты.
        ```
        {
            "messageType":"NEXT_STEP_OF_ROUND",
            "content":
                {
                    "roomCode": 1,
                    "moneyValue": 100
                },
            "author": "ws://192.168.1.116:8080/room/websocket",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"bh"
        }
        ```
  11. Клиент получает отправленное сообщение и изменяет этап игры в комнате на `GameState.FLOP`. Затем клиент считывает поле `moneyValue` и обновляет значение `GameManager.bank` значением из `moneyValue`
  12. Переходим на следующий этап игры

**5. Третий этап игры: Флоп**
  1. Клиент отправляет серверу три запроса с типом `MessageType.DrawCard` для получения первых трех карт на стол (общие карты, которые видят все)
        ```
        {
            "messageType":"DRAW_CARD",
            "content":
                {
                    "roomCode": 1,
                    "userLogin": null
                },
            "author":"bh",
            "datetime":"2023-01-20T20:23:36.724",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
        > Заметим, что в этом запросе поле `userLogin` = null. Это значит, что запрос формируется на карту ДЛЯ СТОЛА, а не для конкретного игрока
   
  2. Сервер получает отправленные сообщения, выбирает случайные карты из колоды в комнате, удаляет выбранные карты из колоды в комнате и на каждое полученное сообщение отправляет всем клиентам сообщения с типом `MessageType.DrawCard`. В этих сообщениях содержаться сериализованные поля класса [Card](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/Card.java) - [CardSuit](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/CardSuit.java) (масть карты) и [CardNumber](backend/PolyPokerServer/src/main/java/com/beathuntercode/polypokerserver/logic/CardNumber.java) (номер карты)
        ```
        {
            "messageType":"DRAW_CARD",
            "content":
                {
                    "roomCode":1,
                    "userLogin": null,
                    "userName":null,
                    "moneyValue":0,
                    "cardSuit":"SPADES",
                    "cardNumber":"TEN",
                    "roomPlayersList":null
                },
            "author":"ws://192.168.1.116:8080/room/websocket",
            "dateTime":"2023-01-20T20:23:37.5796895",
            "receiver":"bh"
        }
        ``` 
  3. Клиент получает отправленные сервером сообщения с информацией о картах для стола. Клиенту нужно сделать проверку
       - если `userLogin` = null, то значит пришла общая карта для стола
       - если `userLogin` != null, значит пришла карта для конректного игрока
        
        Затем клиент обновляет информацию о картах на столе: на столе в игровой комнате должны появится изображения первых трех общих карт.
  4. Клиент отправляет серверу сообщение с типом `MessageType.PLAYER_MUST_MAKE_BET`, чтобы узнать, кто следующий должен сделать ставку. В поле `moneyValue` нужно вставить минимальную ставку, которую должен сделать игрок
        ```
        {
            "messageType":"PLAYER_MUST_MAKE_BET",
            "content":
                {
                    "roomCode": 1
                    "moneyValue": 20
                },
            "author":"bh",
            "dateTime":"2023-01-20T20:23:37.5796895",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  5. Сервер получает отправленное клиентом сообщение, проверяет список игроков в комнате и находит первого игрока, у которого текущая ставка меньше `moneyValue` и отправляет всем клиентам сообщение с типом `MessageType.PLAYER_MUST_MAKE_BET`
        ```
        {
            "messageType":"PLAYER_MUST_MAKE_BET",
            "content":
                {
                    "roomCode": 1
                    "userLogin": "bh"
                    "moneyValue": 20
                },
            "author":"ws://192.168.1.116:8080/room/websocket",
            "dateTime":"2023-01-20T20:23:37.5796895",
            "receiver":"bh"
        }
        ```
  6. Клиент получает отправленное сервером сообщение и
      - если `userLogin` совпадает с логином текущего клиента, то на пользовательском интерфейсе клиента появляется надпись `Сделайте ставку (минимум: moneyValue - <Player.currentStake текущего клиента>)` и активируется кнопка `BET`. Принажатии на эту кнопку, на клиенте появляется диалоговое окно с вводом суммы для ставки. При нажатии на кнопку `OK` в этом диалоговом окне в пользовательском интерфейсе клиента рядом с местом клиента появляется размер ставки, которую он поставил и увеличивает значение поля `Player.currentStake`. Затем клиент отправялет серверу сообщение с типом `MessageType.PLAYER_MAKE_BET`
  7. Сервер получает присланное сообщение, увеличивает текущую ставку игрока (увеличивает значение поля `Player.currentStake`) и отправляет всем клиентам сообщение с типом `MessageType.PLAYER_MAKE_BET`
        ```
        {
            "messageType":"PLAYER_MAKE_BET",
            "content":
                {
                    "roomCode": 1,
                    "userLogin": "bh",
                    "moneyValue: 10
                },
            "author": "ws://192.168.1.116:8080/room/websocket",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"bh"
        }
        ```
  8. Клиент получает отправленное сервером сообщение и
        - если `userLogin` совпадает с логином текущего клиента то клиент игнорирует сообщение
        - если `userLogin` не совпадает с логином текущего клиента то клиент ищет в списке игроков в комнате игрока по пришедшему логину и обновляет его поле `Player.currentStake`, и также обновляет пользовательский интерфейс - рядом с местом игрока должно обновится значение его текущей ставки
  9. Далее повторяются шаги с 4 по 8, пока ставки всех игроков не уравняются.
  10. Когда сервер в очередной раз будет проверять ставки игроков и в этот раз все поля `Player.currentStake` будут равны, сервер переводит комнату в следующий этап игры - изменяет этап на `GameState.TERN`. При этом, в поле `MessageContent.moneyValue` клиент помещает текущее значение банка комнаты.
        ```
        {
            "messageType":"NEXT_STEP_OF_ROUND",
            "content":
                {
                    "roomCode": 1,
                    "moneyValue": 100
                },
            "author": "ws://192.168.1.116:8080/room/websocket",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"bh"
        }
        ```
  11. Клиент получает отправленное сообщение и изменяет этап игры в комнате на `GameState.TERN`. Затем клиент считывает поле `moneyValue` и обновляет значение `GameManager.bank` значением из `moneyValue`
  12. Переходим на следующий этап игры
   
**6. Четвертый этап игры: Тёрн**
  1. Клиент отправляет серверу запроса с типом `MessageType.DrawCard` для получения четвертой карты на стол (общей карты, которые видят все)
  2. Повторяется алгоритм из 5ого пункта со 2ого шага по 10
  3. Клиент получает отправленное сообщение и изменяет этап игры в комнате на `GameState.RIVER`. Затем клиент считывает поле `moneyValue` и обновляет значение `GameManager.bank` значением из `moneyValue`
  4.  Переходим на следующий этап игры

**7. Пятый этап игры: Ривер**
  1. Клиент отправляет серверу запроса с типом `MessageType.DrawCard` для получения пятой карты на стол (общей карты, которые видят все)
  2. Повторяется алгоритм из 5ого пункта со 2ого шага по 10
  3. Клиент получает отправленное сообщение и изменяет этап игры в комнате на `GameState.SHOWDOWN`. Затем клиент считывает поле `moneyValue` и обновляет значение `GameManager.bank` значением из `moneyValue`
  4.  Переходим на следующий этап игры

**8. Шестой этап игры: Шоудаун**
  1. Клиент проходится по списку игроков в комнате и с использованием полей `Player.card1` и `Player.card2` вскрывает карты у всех игроков в комнате. Клиент обновляет пользовательский интерфейс и заменяет картинки рубашек карт у всех игроков на картинки их настоящих карт
  2. Клиент отправляет серверу сообщение с типом `MessageType.WINNER_PLAYER`
        ```
        {
            "messageType":"WINNER_PLAYER",
            "content":
                {
                    "roomCode": 1
                },
            "author": "bh",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  3. Сервер получает отправленное сообщение, вычисляет победную комбинацию и игрока-победителя в комнате и отправляет всем клиентам сообщение с типом `MessageType.WINNER_PLAYER`
        ```
        {
            "messageType":"WINNER_PLAYER",
            "content":
                {
                    "roomCode": 1
                    "userLogin": bh
                },
            "author": "bh",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  4. Сервер обновляет в Базе Данных для `User`, у которого значение поля `User.login` равно значению поля `userLogin` из присланного сообщения, следующие поля
      - `UserStatistic.currentCoinsCount` - увеличивается на значение поля `moneyValue` из присланного клиентом сообщения
      - `UserStatistic.totalEarn` - увеличивается на значение поля `moneyValue` из присланного клиентом сообщения
      - `UserStatistic.totalGamesPlayed` - увеличивается на 1
      - `UserStatistic.totalEarn` - увеличивается на 1
  5. На клиенте появляется диалоговое окно с объявление победителя и размера выигрыша победителя. При нажатии кнопки `OK` в диалоговом окне клиент отправляет серверу сообщение с типом `MessageType.PLAYER_ROOM_EXIT`
        ```
        {
            "messageType":"PLAYER_ROOM_EXIT",
            "content":
                {
                    "roomCode": 1
                    "userLogin: "bh"
                },
            "author": "bh",
            "datetime":"2023-01-20T14:19:51.28",
            "receiver":"ws://192.168.1.116:8080/room/websocket"
        }
        ```
  6. Сервер получает отправленное клиентом сообщение и удаляет игрока из списка игроков в комнате
  7. Клиент покидает комнату и перемещается в Главное Меню.
