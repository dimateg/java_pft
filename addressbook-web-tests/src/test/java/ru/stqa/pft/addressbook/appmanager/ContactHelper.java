package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

    private ApplicationManager app;

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void getHomePage() {
        click(By.linkText("home"));
    }

    public void fillContactForm(ContactData contactData, boolean isGroupPresented) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"), contactData.getEmail());

        if (isGroupPresented) {
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            } else {
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

    public void addPage() {
        click(By.linkText("add new"));
    }

    public void gotoContactEdit(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void gotoContactEditById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void deleteContact() {
        click(By.xpath("//form[2]/input[2]"));
    }


    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
        }


    public void create(ContactData contact, boolean isGroupPresented) {
        addPage();
        fillContactForm(contact, isGroupPresented);
        submitContactForm();
        returnToContactPage();
    }

    public void modify(ContactData contact, boolean isGroupPresented) {
        selectContactById(contact.getId());
        gotoContactEditById(contact.getId());
        fillContactForm((contact), isGroupPresented);
        submitContactModificationForm();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        gotoContactEditById(contact.getId());
        deleteContact();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }


    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("[name=entry]"));
        for (WebElement element : elements) {
            List<WebElement> names = element.findElements(By.tagName("td"));
            String lastname = names.get(1).getText();
            String firstname = names.get(2).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
        }
        return contacts;
    }
}

