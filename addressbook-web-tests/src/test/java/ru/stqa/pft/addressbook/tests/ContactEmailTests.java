package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().HomePage();
        if(app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Dima").withLastname("Ivanov").withNickname("dimateg").withHome("84951471818").withMobile("89121865498").withWork("123456789").withAddress("Москва, ул. Ленина, д. 1").withEmail("test@gmail.com").withGroup("test2"), true);
        }
    }

    @Test
    public void testContactEmail() {
        app.goTo().HomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getEmail(), equalTo(contactInfoFromEditForm.getEmail()));
    }

}
