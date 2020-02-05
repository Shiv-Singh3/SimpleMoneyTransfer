package simpleMoneyTransfer.main.swagger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.BeanConfig;
import simpleMoneyTransfer.webServices.MessageResource;
import simpleMoneyTransfer.constants.CommonConstants;
import simpleMoneyTransfer.constants.ConfigConstants;

final class SwaggerServletContextListener implements ServletContextListener {

    SwaggerServletContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {

        BeanConfig beanConfig = getBeanConfig();
        event.getServletContext().setAttribute(ConfigConstants.ATTRIBUTE_READER, beanConfig);
        event.getServletContext().setAttribute(ConfigConstants.ATTRIBUTE_SWAGGER, beanConfig.getSwagger());
        event.getServletContext().setAttribute(ConfigConstants.ATTRIBUTE_SCANNER, ScannerFactory.getScanner());
    }

    private BeanConfig getBeanConfig() {

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(ConfigConstants.BEAN_CONFIG_VERSION);
        beanConfig.setSchemes(new String[] { ConfigConstants.BEAN_CONFIG_PROTOCOL });
        beanConfig.setHost(ConfigConstants.BEAN_CONFIG_HOST + CommonConstants.COLON + ConfigConstants.BEAN_CONFIG_PORT);
        beanConfig.setBasePath(ConfigConstants.BEAN_CONFIG_BASE_PATH);
        beanConfig.setResourcePackage(MessageResource.class.getPackage().getName());
        beanConfig.setTitle(ConfigConstants.BEAN_CONFIG_TITLE);
        beanConfig.setDescription(ConfigConstants.BEAN_CONFIG_DESCRIPTION);
        beanConfig.setScan(ConfigConstants.BEAN_CONFIG_SET_SCAN);

        return beanConfig;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
