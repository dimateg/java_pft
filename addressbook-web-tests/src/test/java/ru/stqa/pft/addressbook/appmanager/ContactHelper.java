package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean isGroupPresented) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"), contactData.getEmail());

        if(isGroupPresented){
            if(contactData.getGroup() != null){
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }else {
                Assert.assertTrue(isElementPresent(By.name("new_group")));
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactForm() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void submitContactModificationForm() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }


    public void returnToContactPage() {
        click(By.linkText("home page"));
    }

    public void gotoAddContactPage() {
        click(By.linkText("add new"));
    }

    public void gotoContactEdit() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void deleteContact() {
        click(By.xpath("//form[2]/input[2]"));
    }

    public void createContact(ContactData contact, boolean isGroupPresented) {
        gotoAddContactPage();
        fillContactForm(contact, isGroupPresented);
        submitContactForm();
        returnToContactPage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }
}
