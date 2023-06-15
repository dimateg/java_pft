package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification(){
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().gotoContactEdit();
        app.getContactHelper().fillContactCreation(new ContactData("Dima", "Ivanov", "dimateg", "84951471818", "89121865498", "test@gmail.com"));
        app.getContactHelper().submitContactModificationForm();
    }
}
