package test.java;

import main.java.ContactList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class ContactListTest {

    private ContactList fillContacts() {
        ContactList contactList = new ContactList();
        contactList.addPerson("Diana", "88005553535", "+79006663636");
        contactList.addPerson("Tom", "*256#");
        contactList.addPerson("Sberbank", "9-00");
        contactList.addPerson("Service", "01", "02", "03", "911");
        return contactList;
    }

    @Test
    public void testAddPerson() {
        ContactList contactList = new ContactList();
        contactList.addPerson("Diana", "88005553535", "89006663636");
        Set<String> phones = new HashSet<>();
        phones.add("88005553535");
        phones.add("89006663636");
        ContactList.Person person = new ContactList.Person("Diana", phones);
        Assertions.assertTrue(contactList.contains(person));
    }

    @Test
    public void testAddPhone() {
        ContactList contactList = fillContacts();
        contactList.addPhoneForPerson("Tom", "*164#");
        //illegal phone
        contactList.addPhoneForPerson("Tom", "@234-321");
        Assertions.assertTrue(contactList.contains("Tom"));
        Set<String> phones = new HashSet<>();
        phones.add("*256#");
        phones.add("*164#");
        Assertions.assertEquals(phones, contactList.findPhonesOfPerson("Tom"));
    }

    @Test
    public void testDeletePerson() {
        ContactList contactList = fillContacts();
        contactList.deletePerson("Tom");
        Set<String> phones = new HashSet<>();
        phones.add("*256#");
        ContactList.Person person = new ContactList.Person("Tom", phones);
        Assertions.assertFalse(contactList.contains(person));
    }

    @Test
    public void testDeletePhone() {
        ContactList contactList = fillContacts();
        contactList.deletePhoneFromPerson("Service", "02");
        Assertions.assertTrue(contactList.contains("Service"));
        Set<String> phones = new HashSet<>();
        phones.add("01");
        phones.add("03");
        phones.add("911");
        Assertions.assertEquals(phones, contactList.findPhonesOfPerson("Service"));
    }

    @Test
    public void testFindPhonesOfPerson() {
        ContactList contactList = fillContacts();
        ContactList.Person person = contactList.findPersonByPhone("9-00");
        Assertions.assertTrue(contactList.contains(person));
        Assertions.assertTrue(person.containsPhone("9-00"));
    }

    @Test
    public void testFindPersonByPhone() {
        ContactList contactList = fillContacts();
        Set<String> expectedPhones = new HashSet<>();
        expectedPhones.add("*256#");
        Set<String> phonesOfPerson = contactList.findPhonesOfPerson("Tom");
        Assertions.assertEquals(expectedPhones, phonesOfPerson);
    }

    @Test
    public void testEmptyPerson() {
        ContactList contactList = fillContacts();
        ContactList temp = fillContacts();
        contactList.addPerson(null);
        Assertions.assertEquals(temp, contactList);
    }

}
