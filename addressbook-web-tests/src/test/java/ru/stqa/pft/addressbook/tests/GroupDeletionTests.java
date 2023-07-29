package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.*;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupDeletionTests() {
        Groups before = app.db().groups();
        GroupData deletedGroup = before.iterator().next();
        app.goTo().GroupPage();
        app.group().delete(deletedGroup);
        assertEquals(app.group().count(), before.size() - 1);
        Groups after = app.db().groups();
        assertThat(after, equalTo(before.withOut(deletedGroup)));
        verifyGroupListInUI();
    }


}
