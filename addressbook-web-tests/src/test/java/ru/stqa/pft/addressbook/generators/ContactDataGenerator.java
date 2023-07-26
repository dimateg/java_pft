package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Group count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contact = generateContacts(count);
        if(format.equals("json")) {
            saveAsJson(contact, new File(file));
        } else {
            System.out.println("Unrecognized format" + format);
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }


    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        File photo = new File("src/test/resources/stru.png");
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData()
                    .withFirstname(String.format("Firstname %s", i))
                    .withLastname(String.format("Lastname %s", i))
                    .withNickname(String.format("Nickname %s", i))
                    .withHome(String.format("8495169504%s", i))
                    .withMobile(String.format("8925169504%s", i))
                    .withWork(String.format("8907549654%s", i))
                    .withAddress(String.format("Address %s", i))
                    .withEmail(String.format("Email %s", i))
                    .withEmail2(String.format("Email2 %s", i))
                    .withEmail3(String.format("Email3 %s", i))
                    .withGroup("test1")
                    .withPhoto(photo));

        }
        return contacts;
    }
}
