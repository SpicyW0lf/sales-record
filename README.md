# sales-record

[Доменная модель](https://miro.com/app/board/uXjVNRh7DPk=/?share_link_id=991775473069)

## Логика работы

Сервис работает на учет товаров и операции над ними. Пока существует несколько ролей
пользователей - это админ и продавец. Админ ответственнен за добавление новых сотрудников в систему.
Продавец осуществляет операции по продаже товаров. Работает так:
1. дергается `/purchase/start`, после чего начинается этап добавления товаров, если еще не начато, иначе выдается ошибка.
Текущим `purchase` считается запись из таблицы purchase, total у которой = 0.
2. товары добавляются (по product_code) ~~_еще не реализовано_~~
3. дергается `/purchase/stop`, проверяются добавленные товары в purchase_items,
если товаров добавлено не было, запись сразу удаляется, иначе происходит подсчет полученной выручки, обновляется поле
total у purchase и поле qty у всех проданных продуктов.
