/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.admin.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import persistence.models.Authority;

/**
 *
 * @author sergio
 */
@Component
public class AuthorityStringConverter implements Converter<Authority, String> {

    @Override
    public String convert(Authority authority) {
        return authority.getId().toString();
    }
}
