package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification(){
        app.getNavigationHelper().gotoHomePage();
        if(! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Dima", "Ivanov", "dimateg", "84951471818", "89121865498", "test@gmail.com", "test1"), true);
        }
        app.getContactHelper().gotoContactEdit();
        app.getContactHelper().fillContactForm(new ContactData("Dima", "Ivanov", "dimateg", "84951471818", "89121865498", "test@gmail.com", "test1"), false);
        app.getContactHelper().submitContactModificationForm();
    }
}
