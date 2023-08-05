package ru.stqa.pft.mantis.appmanager;

public class NavigationHelper extends HelperBase {
    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void home() {
        wd.get(app.getProperty("web.baseUrl"));
    }
}