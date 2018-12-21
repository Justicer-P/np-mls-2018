package com.sf.marathon.np;

import com.sf.marathon.np.index.domain.EsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagerApplication {

    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger","slf4j");
        SpringApplication.run(ManagerApplication.class, args);
        String url =   EsConfig.getInstance().getEsUrl();
        System.out.println("url = " + url);

    }

}
