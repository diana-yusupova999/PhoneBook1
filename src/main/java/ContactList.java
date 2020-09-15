package main.java;

import java.util.*;
import java.util.function.Function;

//Этот класс наследуется от коллекции ArrayList с типом элементов Person.
//Значит:
//экземпляр этого класса может содержать объекты Person,
//Этот класс имеет все методы класса ArrayList
//Этот класс также наследуется от List, Collections, Iterable и от прочих предков ArrayList
public class ContactList extends ArrayList<ContactList.Person> {

    //добавление человека
    //name - имя человека
    //phones - номера телефонов человека(можно добавить один номер, несколько через запятую, строковый массив номеров)
    public boolean addPerson(String name, String... phones) {
        if (name == null) return false;
        //новый объект человеека
        Person person = new Person(name);
        //добавление номеров для человека.
        Arrays.asList(phones).forEach(phone -> addPhoneForPerson(person, phone));
        //Добавляем получившийся объект Person
        return add(person);
    }

    //удаление человека по экземпляру класса Person
    public boolean deletePerson(Person person){
        return remove(person);
    }

    //удаление человека по имени человека
    public boolean deletePerson(String name) {
        //тут мы находим экземпляр класса Person по имени
        return remove(findPersonByName(name));
    }

    //добавление номера телефона человеку по экземпляру
    public boolean addPhoneForPerson(Person person, String phone) {
        //если номер телефона корректеного вида, добавить его
        if (person != null && phoneIsMatches(phone)) {
            return person.addPhone(phone);
        } else return false;
    }

    //добавление номера телефона человеку по имени
    public boolean addPhoneForPerson(String name, String phone) {
        //тут мы находим экземпляр класса Person по имени
        return addPhoneForPerson(findPersonByName(name), phone);
    }

    //удаление номера телефона из списка номеров человека по экземпляру
    public boolean deletePhoneFromPerson(Person person, String phone) {
        return person.removePhone(phone);
    }

    //удаление номера телефона из списка номеров человека по имени
    public boolean deletePhoneFromPerson(String name, String phone) {
        //тут мы находим экземпляр класса Person по имени
        return deletePhoneFromPerson(findPersonByName(name), phone);
    }

    //нахождение экземпляра Person по имени
    private Person findPersonByName(String name) {
        return findPerson(person -> person.nameEquals(name));
    }

    //нахождение экземпляра Person по номеру телефона
    public Person findPersonByPhone(String phone) {
        return findPerson(person -> person.containsPhone(phone));
    }

    //общий метод нахождения Person.
    //аргумент - интерфейс Function<Person, Boolean>
    //он позволяет передавать функцию/метод в качестве аргумента функции/метода
    //это нам нужно для того, чтобы не писать одно и то же в каждом методе по поиску Person
    private Person findPerson(Function<Person, Boolean> function) {
        //создаем пустой объект Person (в котором name = null, а phones = пустое множество/emptySet)
        //и добавляем его в массив одного эллемента для того,
        //чтобы можно было менять значение результата внутри лямбда выражения
        final Person[] result = {Person.emptyPerson()};
        //для каждого элемента Person в данном экземпляре ContactList
        forEach(person -> {
            //если некая функция возвращает true (например person.nameEquals(name))
            if (function.apply(person)) {
                //первый эллемент становиться найденным person
                result[0] = person;
            }
        });
        //возвращаем первый эллемент (либо найденный Person, либо пустой Person)
        return result[0];
    }

    //нахождение множества номеров телефонов человека по экземпляру
    public Set<String> findPhonesOfPerson(Person person) {
        if (person == null) return Collections.emptySet();
        return person.getPhones();
    }

    //нахождение множества номеров телефонов человека по имени
    public Set<String> findPhonesOfPerson(String name) {
        //тут мы находим экземпляр класса Person по имени
        return findPhonesOfPerson(findPersonByName(name));
    }

    //переопределение метода contains от класса ArrayList
    //добавляем функциональность: 'в этом классе не может лежать пустого Person'
    //super.contains(o) - родительская функциональность
    @Override
    public boolean contains(Object o) {
        if (o.equals(Person.emptyPerson())) return false;
        return super.contains(o);
    }

    public boolean contains(String name) {
        return contains(findPersonByName(name));
    }

    //проверка телефона на корректность
    public static boolean phoneIsMatches(String phone) {
        return phone != null && phone.matches("[\\d-+*#]*");
    }

    //класс, описывающий человека с именем и списком номеров телефонов
    public static class Person {
        private String name;
        private Set<String> phones;
        //конструктор объекта Person c возможностью задать имя и готовый список номеров телефонов
        public Person(String name, Set<String> phones) {
            this.name = name;
            this.phones = phones;
        }

        //конструктор объекта Person c возможностью задать имя. список номеров - изначально пустая коллекция
        public Person(String name) {
            this(name, new HashSet<>());
        }

        //возвращает экземпляр пустого Person
        public static Person emptyPerson(){
            return new Person(null, Collections.emptySet());
        }

        //задает этому экземпляру параметры другого экземпляра Person
        public void set(Person person) {
            this.name = person.name;
            this.phones = person.phones;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<String> getPhones() {
            return phones;
        }

        public void setPhones(Set<String> phones) {
            this.phones = phones;
        }

        public boolean addPhone(String phone) {
            return phones.add(phone);
        }

        public boolean removePhone(String phone) {
            return phones.remove(phone);
        }

        public String getPhone(int i) {
            return phones.toArray(new String[0])[i];
        }

        //эквивалентно ли имя этого экземпляра заданному аргументу?
        public boolean nameEquals(String name) {
            if (name != null && !name.isEmpty()) {
                return this.name.equals(name);
            }
            return false;
        }

        //содержит ли этот экземпляр Person в своем множестве заданный телефон?
        public boolean containsPhone(String phone) {
            return phones.contains(phone);
        }

        //переопределения методов Object:

        //эквивалентность объектов
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(name, person.name) &&
                    Objects.equals(phones, person.phones);
        }

        //хэш-код объекта
        @Override
        public int hashCode() {
            return Objects.hash(name, phones);
        }

        //строковое представление объекта
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", phones=" + phones +
                    '}';
        }
    }
}