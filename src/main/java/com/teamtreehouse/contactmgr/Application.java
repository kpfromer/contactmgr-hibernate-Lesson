package com.teamtreehouse.contactmgr;

import com.teamtreehouse.contactmgr.model.Contact;
import com.teamtreehouse.contactmgr.model.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kpfromer on 3/7/17.
 */
public class Application {
    // Hold a reusable reference to a SessionFactory (Since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        //Create a StandardServiceRegistry Object

        //configure loads hibernate's configuration file from default location (you can change the default location by adding a string to configure)
        //build will construct a StandardServiceRegistryBuilder object
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        //This contains all the ORM mappings loaded from the annotated entities
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("Kyle", "Pfromer")
                .withEmail("g@gmail.com")
                .withPhones(new ArrayList<Phone>(Arrays.asList(
                        new Phone("landline", 1112223333L),
                        new Phone("mobile", 1231231234L)
                )))
                .build();

        int id = save(contact);

        //Display a list of contacts before the update
        System.out.printf("%nBefore Update%n");
        for (Contact contact1 : fetchAllContacts()) {
            System.out.println(contact1);
        }

        //get the persisted contact (getting the contact from the database, which is the contact above)
        Contact c = findContactById(id);

        //update the contact
        c.setFirstName("Ed");

        //persist the changes (needs proper id)
        update(c);

        //display a list of contacts after the update
        System.out.printf("%nAfter Update%n");
        System.out.printf("%nBefore Update%n");
        for (Contact contact1 : fetchAllContacts()) {
            System.out.println(contact1);
        }

        delete(c);

        System.out.printf("%nAfter Delete%n");
        System.out.printf("%nBefore Update%n");
        for (Contact contact1 : fetchAllContacts()) {
            System.out.println(contact1);
        }

        System.out.println("Phone of id 1: " + findContactById(9).getPhones().get(1));

    }

    private static int save(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        //Begin a transaction, allows for rollbacks if something goes wrong (add compile 'javax.transaction:jta:VERSION!!!' to gradle build file)
        session.beginTransaction();

        //Use the session to save the contact
        int id = (int) session.save(contact);

        //commit the transaction
        session.getTransaction().commit();

        //close the session
        session.close();

        return id;
    }

    private static void delete(Contact contact) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        //Use the session to update the contact, via the id
        session.delete(contact);

        session.getTransaction().commit();

        session.close();
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
//        //Open a session
//        Session session = sessionFactory.openSession();
//
//        //Create Criteria for narrowing search (can use strings to say find item with email that matches k@gmail.com)
//        Criteria criteria = session.createCriteria(Contact.class);
//
//        //Get a list of Contact objects according to the Criteria object
//        List<Contact> contacts = criteria.list();
//
//        //close session
//        session.close();
//
//        return contacts ;

        //THE CURRENT CODE IS DEPRECATED WITH VERSION 5.2+ USE
//        private static List<Contact> fetchAllContacts() {
        // Open a session
        Session session = sessionFactory.openSession();

        // DEPRECATED: Create Criteria
        // Criteria criteria = session.createCriteria(Contact.class);

        // DEPRECATED: Get a list of Contact objects according to the Criteria object
        // List<Contact> contacts = criteria.list();

        // UPDATED: Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // UPDATED: Create CriteriaQuery
        CriteriaQuery<Contact> criteria = builder.createQuery(Contact.class);

        // UPDATED: Specify criteria root
        criteria.from(Contact.class);

        // UPDATED: Execute query
        List<Contact> contacts = session.createQuery(criteria).getResultList();

        // Close the session
        session.close();

        return contacts;
    }


    private static Contact findContactById(int id) {

        Session session = sessionFactory.openSession();

        //Retrieve the persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);

        session.close();

        return contact;
    }

    private static void update(Contact contact) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        //Use the session to update the contact, via the id
        session.update(contact);

        session.getTransaction().commit();

        session.close();
    }

}