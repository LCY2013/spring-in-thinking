package org.fufeng.springnative.scopes;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Configuration
public class ScopesConfiguration {

}

@RestController
class ContextHttpController {
    private final RequestContext context;

    //@Lazy
    public ContextHttpController(RequestContext context) {
        this.context = context;
    }

    @GetMapping("/scopes/context")
    public String uuid() {
        return this.context.getUuid();
    }
}

@Component
@RequestScope
class RequestContext {

    private final String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }
}
