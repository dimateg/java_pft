package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().HomePage();
        if(app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Dima").withLastname("Ivanov").withNickname("dimateg").withHome("84951471818").withMobile("89121865498").withWork("123456789").withAddress("Москва, ул. Ленина, д. 1").withEmail("test@gmail.com").withGroup("test2"), true);
        }
    }

    @Test
    public void testContactDeletion(){
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().HomePage();
        Contacts after = app.contact().all();

        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.withOut(deletedContact)));
    }


}
