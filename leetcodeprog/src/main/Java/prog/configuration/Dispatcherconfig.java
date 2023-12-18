package prog.configuration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
public class Dispatcherconfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {Webconfig.class};//тепер знає де знаїодиться java-конфігурація
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {//запускається при старті застосунку
        super.onStartup(servletContext);
        registerHiddenFieldFilter(servletContext);
    }
    private void registerHiddenFieldFilter(ServletContext aContext){
        aContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null,true,"/*");
    }
}
