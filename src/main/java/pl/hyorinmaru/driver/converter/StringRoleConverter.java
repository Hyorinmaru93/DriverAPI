package pl.hyorinmaru.driver.converter;

import org.springframework.core.convert.converter.Converter;
import pl.hyorinmaru.driver.model.Role;

public class StringRoleConverter implements Converter<String, Role> {

    @Override
    public Role convert(String roleName) {
        return null;
    }
}
