package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  //Можем пропустить фальшивую персону в стриме, не удаляя ее
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  // В Set и так хранятся уникальные значения, distinct() можно не использовать
  // Использование стрима тут избыточно, можем просто создать HashSet
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  // Второй getSecondName() заменен на getMiddleName()
  // С помощью стрима можем объединить ФИО и проверить на Null в сокращенном виде
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(),  person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // Емкость словаря в данном случае лучше не ограничивать константой
  // Можем создать словарь с помощью стримов
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // Можно оптимизировать сравнение с помощью стримов
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains);
  }

  // Поле count используется только в одном классе, плохая практика объявлять его на уровне класса
  // Вместо счетчика можно использовать метод count() у стрима
  public long countEven(Stream<Integer> numbers) {
    return numbers
            .filter(num -> num % 2 == 0)
            .count();
  }
}
