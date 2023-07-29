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
        attach(By.name("photo"), contactData.getPhoto());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("work"), contactData.getWork());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());


        if (isGroupPresented) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
        }
            else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
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

    private void selectGroupForAddById(int groupId) {
        click(By.cssSelector(String.format("select[name='to_group'] > option[value='%s']", groupId)));
    }

    private void addSelectedContactToGroup() {
        click(By.xpath("//input[@value='Add to']"));
    }

    private void selectGroupPageById(int groupId) {
        click(By.cssSelector(String.format("select[name='group'] > option[value='%s']", groupId)));
    }

    private void removeSelectedContactFromGroup() {
        click(By.xpath("//input[@name='remove']"));
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

    public void addToGroup(int contactId, int groupId) {
        selectContactById(contactId);
        selectGroupForAddById(groupId);
        addSelectedContactToGroup();
        contactCache = null;
    }

    public void removeFromGroup(int contactId, int groupId) {
        selectGroupPageById(groupId);
        selectContactById(contactId);
        removeSelectedContactFromGroup();
    }


    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;


    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("[name=entry]"));
        for (WebElement element : elements) {
            List<WebElement> names = element.findElements(By.tagName("td"));
            String lastname = names.get(1).getText();
            String firstname = names.get(2).getText();
            String address = names.get(3).getText();
            String allEmails = names.get(4).getText();
            String allPhones = names.get(5).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        gotoContactEditById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3).withHome(home).withMobile(mobile).withWork(work);
    }
}

