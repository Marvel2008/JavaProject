package prog.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.Model.Person;
import prog.configuration.PersistentConfig;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
public class HibernateFullTest {
    private SessionFactory sessionFactory;

    @BeforeEach
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("Hiber/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @AfterEach
    protected void tearDown() throws Exception {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    @Test
    void save_my_first_object_to_the_bd() {
        Person person= new Person("kkk","sdkadk@gmail.com","kolaps");
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            //
            session.persist(person);

            session.getTransaction().commit();
        }
    }
    @Test
    void checking(){
        Person person= new Person("kkk","sdkadk@gmail.com","kolaps");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            String query = "FROM Person WHERE email = :email AND password = :password";
            List<Person> result = session.createQuery(query, Person.class)
                    .setParameter("email", person.getEmail())
                    .setParameter("password", person.getPassword())
                    .getResultList();
            if (!result.isEmpty()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBasicUsage() {
        // create a couple of events...
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(new Person("Marco's Friend", "Kolaps@gmail.com","pass"));
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Person> result = session.createQuery( "select u from Person u" , Person.class).list();
        for ( Person user : result) {
            System.out.println( "User (" + user.getName() + ") : " + user.getEmail());
        }
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void marco_is_in_the_house() {
        assertThat(1).isGreaterThanOrEqualTo(0);
    }
}
