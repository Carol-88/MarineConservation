package marine.conservation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI marineConservationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Marine Conservation Management API")
                        .description("API for managing marine events, projects, species, volunteers and habitats")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Ironhack Team")
                                .email("contact@ironhack.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0")));
    }

    @Bean
    public GroupedOpenApi volunteerGroup() {
        return GroupedOpenApi.builder()
                .group("Volunteers")
                .pathsToMatch("/api/volunteers/**")
                .build();
    }

    @Bean
    public GroupedOpenApi speciesGroup() {
        return GroupedOpenApi.builder()
                .group("Marine Species")
                .pathsToMatch("/api/species/**")
                .build();
    }

    @Bean
    public GroupedOpenApi projectsGroup() {
        return GroupedOpenApi.builder()
                .group("Conservation Projects")
                .pathsToMatch("/api/projects/**")
                .build();
    }

    @Bean
    public GroupedOpenApi eventsGroup() {
        return GroupedOpenApi.builder()
                .group("Conservation Events")
                .pathsToMatch("/api/events/**")
                .build();
    }

    @Bean
    public GroupedOpenApi habitatsGroup() {
        return GroupedOpenApi.builder()
                .group("Habitats")
                .pathsToMatch("/api/habitats/**")
                .build();
    }
}