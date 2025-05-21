# Первый рефакторинг

### 1

Исправил логику для экрана ввода кода подтверждения. Теперь код должен вводиться корректно

### 2

Методы не использующиеся за пределами класса были помечены private.

### 3

Заменил текущую формат директорий на:

- components
- data
- ui
- utils
- viewmodels

### 5

Заменил поля на private в UserPreferencesUtils. Добавить const не получилось, так как поля не
являются примитивными.

### 6

Заменил все localDeliveryColors на цвета из MaterialTheme.

### 7

Вынес data и sealed классы из viewmodels в отдельные файлы.

### 8

Убрал Composable-функцию с названием view для каждого экрана (ProfileView, UserDataView и пр.)

### 12

Убрал в аргументах передачу типа экрана и разделил экран ввода кода подтверждения на отдельных два
(CodeConfirmEmailScreen и CodeConfirmPhoneScreen).

### 11 и 13

Убрал большую часть компонентов, которые нужные были в основном для просмотра результата в Preview.
Переместил их содержимое напрямую в screens.

- Оставил в CodeConfirm компоненты, так как они теперь переиспользуются для 2-х экранов phone и
  email.
- Оставил в Profile компоненты, так как часть тоже используется несколько раз.
- Оставил в UserData компоненты, тоже переиспользуются.

# Второй рефакторинг

### 1

Убрал все data и sealed классы из ui в data.

### 2

Убрал неиспользуемую функцию requireValue() из класса-обертки Event для LiveData.

### 3

Заменил запись значения. Теперь вместо setValue используется postValue. Но не везде, я столкнулся с
проблемой, описал ее в последнем блоке.

### 4

Добавил больше документации к функциям.

### 5

Убрал не используемые переменные с экрана CodeConfirmPhoneScreen.

### ProfileViewModel и MainViewModel

Заменил код в классе ProfileViewModel. Была livedata и init блок, в котором flow через collect
сохранял данные в livedata.

```kotlin

private val _state = MutableLiveData(ProfileState())
val state: LiveData<ProfileState> = _state

init {
    viewModelScope.launch {

        userPreferencesUtils.userDataFlow.collect { userData ->
            _state.value = _state.value?.copy(
                isLoggedIn = userData.isLoggedIn,
                userName = userData.name
            )
        }
    }
}
```

Заменил на более простой вызов через stateIn, где flow сразу превращается в stateflow без логики в
блоке init.

```kotlin
val state = userPreferencesUtils.userDataFlow.map {
    ProfileState(
        isLoggedIn = it.isLoggedIn,
        userName = it.name
    )
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProfileState())

```

Точно также заменил код и в MainViewModel.

Было:

```kotlin
private val _isLoggedIn = MutableLiveData<Boolean>()
val isLoggedIn: LiveData<Boolean> = _isLoggedIn

init {
    loadData()
}

fun loadData() {
    viewModelScope.launch {
        userPreferencesUtils.userIsLoggedIn().collect {
            _isLoggedIn.value = it
        }
    }
}
```

Стало:

```kotlin
val isLoggedIn = userPreferencesUtils.userDataFlow.map { it.isLoggedIn }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
```

В тестовом задании в стеке технологий была указана LiveData, поэтому, не уверен, что замена livedata
на flow это корректно в данном случае.

Но если нужно только получать данные, мне кажется такая запись будет компактнее.

### Проблема с postValue

У меня возникает проблема при записи состояния в liveData через postValue, когда происходит ввод
текста textField.
При вводе текста с определенной вероятностью курсор может съехать на один символ влево и если зажать
кнопку удаления на клавиатуре, то он последовательно начнет удалять символы пока не застрянет через
определенный промежуток и снова повторно зажимать кнопку.
В общем, ввод текста в textField происходит некорректно.

Дело в том, что раньше я всегда использовал setValue для сохранения данных синхронно, поэтому с
подобной проблемой не сталкивался.
Я примерно понимаю как работает postValue. Сохранение в liveData будет поставлено в очередь и
выполнится когда главный поток будет свободен.

Я бы хотел узнать: есть особая специфика сохранения данных через postValue или я некорректно описал
логику (что больше всего вероятно)?
Если это 2 вариант и он потребует вашего времени для анализа кода для выявления причины, тогда не
нужно этого делать, я буду дальше разбираться в чем заключается проблема.


