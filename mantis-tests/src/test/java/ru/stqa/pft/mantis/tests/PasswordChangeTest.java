package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PasswordChangeTest extends TestBase {
    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws MessagingException, IOException {
        User user = app.db().Users().stream().findAny().orElse(null);
        long now = System.currentTimeMillis();
        int countMsg = 1;
        if (user == null) {
            String newUser = "user" + now;
            String email = newUser + "@localhost.localdomain";
            String password = "password";
            app.registration().start(newUser, email);
            List<MailMessage> mailMessages = app.mail().waitForMail(2, 1000);
            countMsg += 2;
            String confirmationLink = findConfirmationLink(mailMessages, email);
            app.registration().finish(confirmationLink, password);
            user = app.db().Users().stream().findAny().orElse(null);
            app.navigation().home();
        }
        String password = String.format("password%s", now);;
        app.user().adminLogin();
        app.user().resetPassword(user);
        List<MailMessage> mailMessages = app.mail().waitForMail(countMsg, 1000);
        String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(user.getLogin(), password));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}