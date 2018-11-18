package com.development.baseapp.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class <code>{@link UserDataWriterListener}</code> is responsible for write authenticated
 * user information on response's <i>{@link java.io.OutputStream}</i>.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class UserDataWriterListener implements AuthenticationListener {

    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(final UserFullyAuthenticatedEvent authenticationEvent) throws IOException {
        final Authentication authentication = authenticationEvent.getAuthentication();
        final ObjectNode userInfo = mapper.createObjectNode();
        userInfo.put("name", authentication.getName());
        userInfo.putPOJO("details", authentication.getDetails());
        final HttpServletResponse response = authenticationEvent.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getOutputStream().print(userInfo.toString());
    }
}
