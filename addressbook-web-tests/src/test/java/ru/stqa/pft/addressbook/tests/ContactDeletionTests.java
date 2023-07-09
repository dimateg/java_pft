package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion(){
        app.getNavigationHelper().gotoHomePage();
        if(! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Dima", "Ivanov", "dimateg", "84951471818", "89121865498", "test@gmail.com", "test1"));
        }
        app.getContactHelper().gotoContactEdit();
        app.getContactHelper().deleteContact();
    }
}
