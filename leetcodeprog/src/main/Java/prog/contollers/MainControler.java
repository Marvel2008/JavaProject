package prog.contollers;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.hibernate.boot.registry.StandardServiceRegistry;
import prog.DTO.PersonDTO;
import prog.Model.Person;
import prog.Service.PersService;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/information")
public class MainControler {

    @Autowired
    private PersService persService;

    private AuthenticationManager authenticationManager;//клас спрінг секюріті


    private SessionFactory sessionFactory;

    @GetMapping()
    public String global(Model model){
        model.addAttribute("person",new Person());
        return "firstgroup/form";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
       if (bindingResult.hasErrors()){
           System.out.println("Невдала валідація");
           return "redirect:/information";
       }
        if (!persService.checkemail(person)){
            System.out.println("Помилка з повтором пошти");
            return "redirect:/information";
        }
        return "redirect:/information";
    }

     //Метод для автоматичного входу користувача після реєстрації
    private void authenticateUserAndSetSession(Person person, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                person.getEmail(),
                person.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
    }
    @GetMapping("/sighin")
    public String check( @ModelAttribute("person") Person person){
                    if (persService.authenticateUser(person)) {
                //req.login(person.getEmail(),person.getPassword());
                Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            if (auth!=null){
                System.out.println(auth.getName());
            }else {
                System.out.println(person.getEmail());
                System.out.println(person.getPassword());
                persService.loadUserByUsername(person.getEmail());
                System.out.println("Authentication object is null");
            }
                return "firstgroup/success";
            }
                return "firstgroup/Fault";
    }
        @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpServletResponse response){
            new SecurityContextLogoutHandler().logout(request,null,null);//видаляємо аутентифікаційний токен користувача та перенаправляємо на головну сторінку.
            return "redirect:/information";
        }
    @PostConstruct
    protected void setUp() throws Exception{
        final StandardServiceRegistry registry= new StandardServiceRegistryBuilder()
                .configure("/Hiber/hibernate.cfg.xml")
                .build();
        try{
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }catch (Exception ex){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    @PreDestroy
    protected void tearDown() throws Exception{
        if(sessionFactory !=null){
            sessionFactory.close();
        }
    }
}
