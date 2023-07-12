package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {


    @Test
    public void testContactCreationTests() throws Exception {
        app.getContactHelper().gotoAddContactPage();
        app.getContactHelper().fillContactForm(new ContactData("Dima", "Ivanov", "dimateg", "84951471818", "89121865498", "test@gmail.com", "test1"), true);
        app.getContactHelper().submitContactForm();
        app.getContactHelper().returnToContactPage();
        app.logOut();
    }


}