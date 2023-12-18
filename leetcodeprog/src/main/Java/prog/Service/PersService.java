package prog.Service;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import prog.Model.Person;
import prog.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersService implements UserDetailsService {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PersonRepository personRepository;

    public boolean authenticateUser(Person person){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            String query = "FROM Person WHERE email = :email AND password = :password";
            List<Person> result = session.createQuery(query, Person.class)
                    .setParameter("email", person.getEmail())
                    .setParameter("password", person.getPassword())
                    .getResultList();
            if (!result.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void create(Person person){
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(person);
            session.getTransaction().commit();
        }
    }
    public boolean checkemail(Person person){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Перевіряємо, чи існує вже користувач з вказаним email
            String checkQuery = "FROM Person WHERE email = :email";
            List<Person> existingUsers = session.createQuery(checkQuery, Person.class)
                    .setParameter("email", person.getEmail())
                    .getResultList();

            if (existingUsers.isEmpty()) {
                // Якщо користувача з вказаним email не існує, тоді зберігаємо його
                //person.setPassword(passwordEncoder.encode(person.getPassword()));
                session.persist(person);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> user = personRepository.findByEmail(email);
        System.out.println(user.toString());
        return user.map(Person::new).orElseThrow(()->new UsernameNotFoundException("User does not exists"));
    }


}
