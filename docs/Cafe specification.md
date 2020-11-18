﻿# Меню кафе

**Цель:** Разработка Web-приложения для формирования и производства заказов в кафе (формирование заказа кассиром - обработка заказа - передача в производство - оплата)

**Функции приложения:**
- Формирование заказа, редактирование, удаление, просмотр содержимого заказа
- Формирование содержимого (элементов) заказа
- Фильтр заказов по ID заказа, по ID сотрудника, статусу заказа

**Уровни доступа к функциям приложения:**

* Для кассиров присутствует стандартный функционал работы приложения: формирование, редактирование (добавление/удаление товаров), удаление, просмотр заказов, формирование содержимого заказов, фильтр статусу заказа

**Функциональные блоки приложения:**
- Блок главного окна приложения. В данном блоке представлены название программы, поле ввода *ID сотрудника*, кнопки: *"Вход", "Выход"*
- Блок окна списка заказов. В данном блоке представлен список заказов в зависимости от ID сотрудника с функциональными кнопками: *"Новый заказ", "Просмотр", "Редактировать заказ", "Удалить заказ", "Фильтр", "Главное меню"*
- Блок окна текущего заказа. В данном блоке представлено содержимое текущего заказа с кнопками: *"Добавить товар", "Удалить товар", "Сохранить", "Отмена"'*
- Блок окна списка товаров, доступных для добавления в заказ. В данном блоке представлен список товаров с кнопками *"Добавить в заказ", "Отмена"*

### Структура данных заказа:
**Заказ**
- 1. Номер (ID) заказа
- 2. ID Кассира
- 3. Статус заказа (новый, в работе, закрыт)

###
**Содержимое заказа**
- 1. Номер (ID) заказа
- 2. (Список): Наименование товара
- 3. (список): Количество
- 4. (список): Цена за шт.

###
**Список товаров**
- 1. ID товара
- 2. Наименование товара
- 3. Цена


###
## 1. Работа с заказами

### 1.1 Формирование заказа кассиром

**Основной сценарий формирования заказа:**
- При запуске приложения отображается форма авторизации работника
- Кассир вводит свой ID, нажимает кнопку "Вход"
- Отображается форма списка заказов данного кассира
- Кассир нажимает кнопку "Новый заказ", создается заказ с автоматическим присвоением очередного номера заказа, даты, и статуса заказа "новый"
- Отображается форма содержимого текущего заказа
- Кассир нажимает кнопку "Добавить товар" - отображается форма выбора товара и его количества для добавления в текущий заказ
- Кассир выбирает товар, вводит количество товара для добавления в заказ, по окончанию выбора жмет кнопку "Добавить в заказ"
- Отображается предыдущая форма содержимого текущего заказа
- Кассир повторяет процедуру добавления товара необходимое количество раз
- По окончании добавления товаров в заказ, кассир нажимает кнопку "Сохранить заказ" на форме текущего заказа
- Отображается форма списка заказов данного кассира с учетом созданного заказа

**Сценарий отмены изменений при формировании заказа:**

- При запуске приложения отображается форма авторизации работника
- Кассир вводит свой ID, нажимает кнопку "Вход"
- Отображается форма списка заказов данного кассира
- Кассир нажимает кнопку "Новый заказ", создается заказ с автоматическим присвоением очередного номера заказа, даты, и статуса заказа "новый"
- Отображается форма содержимого текущего заказа
- Кассир нажимает кнопку "Добавить товар" - отображается форма выбора товара и его количества для добавления в текущий заказ
- Кассир выбирает товар, вводит количество товара для добавления в заказ, по окончанию выбора жмет кнопку "Добавить в заказ"
- Отображается предыдущая форма содержимого текущего заказа
- Кассир повторяет процедуру добавления товара необходимое количество раз
- При необходимости отмены заказа, кассир нажимает кнопку "Отмена"
- Текущий заказ не сохраняется
- Отображается форма списка заказов данного кассира

### 1.2 Редактирование заказа кассиром

**Основной сценарий редактирования заказа:**
- При запуске приложения отображается форма авторизации работника
- Кассир вводит свой ID, нажимает кнопку "Вход"
- Отображается форма списка заказов данного кассира
- Кассир выделяет в списке заказ для редактирования и нажимает кнопку "Редактировать заказ"
- Отображается форма содержимого выбранного текущего заказа
- При необходимости кассир нажимает кнопку "Добавить товар" - отображается форма выбора товара и его количества для добавления в текущий заказ, после выбора необходимых действий, отображается предыдущая форма текущего заказа
- При необходимости кассир выбирает товар для удаления и нажимает кнопку "Удалить товар" - выбранный товар удаляется из списка текущего заказа
- Кассир повторяет процедуру добавления или удаления товара необходимое количество раз
- По окончании редактирования заказа товаров в заказ, кассир нажимает кнопку "Сохранить" на форме текущего заказа
- Отображается форма списка заказов данного кассира с учетом измененного заказа
Сценарий отмены изменений при редактировании заказа:
- При запуске приложения отображается форма авторизации работника
- Кассир вводит свой ID, нажимает кнопку "Вход"
- Отображается форма списка заказов данного кассира
- Кассир выделяет в списке заказ для редактирования и нажимает кнопку "Редактировать заказ"
- Отображается форма содержимого выбранного текущего заказа
- При необходимости кассир нажимает кнопку "Добавить товар" - отображается форма выбора товара и его количества для добавления в текущий заказ, после выбора необходимых действий, отображается предыдущая форма текущего заказа
- При необходимости кассир выбирает товар для удаления и нажимает кнопку "Удалить товар" - выбранный товар удаляется из списка текущего заказа
- Кассир повторяет процедуру добавления или удаления товара необходимое количество раз
- При необходимости отмены внесенных изменений в заказ кассир нажимает кнопку "Отмена"
- Отображается форма списка заказов данного кассира без сохранения изменений заказа

### 1.3 Удаление заказа кассиром

**Основной сценарий удаления заказа:**
- При запуске приложения отображается форма авторизации работника
- Кассир вводит свой ID, нажимает кнопку "Вход"
- Отображается форма списка заказов данного кассира
- Кассир выделяет в списке заказ для удаления и нажимает кнопку "Удалить заказ"
- Отображается форма с запросом подтверждения удаления заказа с кнопками "Удалить" и "Отмена"
- Кассир нажимает кнопку "Удалить"
- Выбранный заказ удаляется, отображается форма списка заказов кассира
- Кассир повторяет процедуру удаления заказа при необходимости
Сценарий отмены удаления заказа:
- При запуске приложения отображается форма авторизации работника
- Кассир вводит свой ID, нажимает кнопку "Вход"
- Отображается форма списка заказов данного кассира
- Кассир выделяет в списке заказ для удаления и нажимает кнопку "Удалить заказ"
- Отображается форма с запросом подтверждения удаления заказа с кнопками "Удалить" и "Отмена"
- Кассир нажимает кнопку "Отмена"
- Выбранный заказ не удаляется, отображается форма списка заказов кассира
- Кассир повторяет процедуру удаления заказа при необходимости

### 1.4 Просмотр заказа кассиром
**Основной сценарий просмотра заказа:**
- При запуске приложения отображается форма авторизации работника
- Кассир вводит свой ID, нажимает кнопку "Вход"
- Отображается форма списка заказов данного кассира
- Кассир выделяет в списке заказ для просмотра и нажимает кнопку "Просмотр"
- Отображается форма содержимого выбранного текущего заказа
- По окончании просмотра заказа, кассир нажимает кнопку "Назад" на форме текущего заказа
- Отображается форма списка заказов данного кассира

## 2. Формирование содержимого заказа

**Основной сценарий формирования содержимого заказа:**
- В форме текущего заказа кассир нажимает кнопку "Добавить товар"
- Отображается форма выбора товара и его количества для добавления в текущий заказ
- Кассир выбирает товар, вводит количество товара для добавления в заказ
- Кассир по окончанию выбора жмет кнопку "Добавить в заказ"

**Сценарий отмены формирования содержимого заказа:**
- В форме текущего заказа кассир нажимает кнопку "Добавить товар"
- Отображается форма выбора товара и его количества для добавления в текущий заказ
- Кассир выбирает товар, вводит количество товара для добавления в заказ
- Для отмены добавления кассир нажимает кнопку "Отмена"
- Отображается форма текущего заказа
Фильтр заказов по ID сотрудника, Дате/времени, статусу

## 3. Фильтр заказа
**Возможность фильтра по ID сотрудника, дате/времени заказа, содержимому заказа и статусу** предусмотрена в форме списка заказов для администратора системы кнопкой "Фильтр" с выбором поля фильтрации

**Возможность фильтра по дате/времени заказа, содержимому заказа и статусу** предусмотрена в форме списка заказов для администратора системы кнопкой "Фильтр" с выбором поля фильтрации