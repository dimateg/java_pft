package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.stream.Collectors;

public class ContactAddedAndDeleteGroup extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        File avatar = new File("src/test/resources/avatar.jpg");
        if (app.db().contacts().size() == 0) {
            app.goTo().HomePage();
            app.contact().create(new ContactData().withFirstname("Dima").withLastname("Ivanov").withNickname("dimateg").withHome("84951471818").withMobile("89121865498").withWork("123456789").withAddress("Москва, ул. Ленина, д. 1")
                    .withEmail("test@gmail.com").withEmail2("test2@gmail.com").withEmail3("test3@gmail.com"), false);

        }

        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test 1"));
        }
    }

    @Test
    public void testContactAddedToGroup() {
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        ContactData contactForAdd = before.iterator().next();
        int contactId = contactForAdd.getId();
        int groupId = groups.iterator().next().getId();


        if (isGroupContainsContact(groupId, contactForAdd)) {
            app.goTo().HomePage();
            app.contact().removeFromGroup(contactId, groupId);
        }

        app.goTo().HomePage();
        app.contact().addToGroup(contactId, groupId);

        Contacts after = app.db().contacts();

        Assert.assertTrue(isGroupContainsContact(groupId, getMovedContact(contactId, after)));
    }

    @Test
    public void testContactRemoveFromGroup() {
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        ContactData contactForRemove = before.iterator().next();
        int contactId = contactForRemove.getId();
        int groupId = groups.iterator().next().getId();

        if (!isGroupContainsContact(groupId, contactForRemove)) {
            app.goTo().HomePage();
            app.contact().addToGroup(contactId, groupId);
        }

        app.goTo().HomePage();
        app.contact().removeFromGroup(contactId, groupId);

        Contacts after = app.db().contacts();

        Assert.assertFalse(isGroupContainsContact(groupId, getMovedContact(contactId, after)));
    }

    private static boolean isGroupContainsContact(int groupId, ContactData contact) {
        return contact.getGroups().stream()
                .map((g) -> new GroupData().withId(g.getId())).collect(Collectors.toSet())
                .contains(new GroupData().withId(groupId));
    }

    private static ContactData getMovedContact(int contactId, Contacts contacts) {
        return contacts.stream().filter(c -> c.getId() == contactId)
                .collect(Collectors.toSet()).iterator().next();
    }
}

