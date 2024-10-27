package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    FooService fooService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws Exception {
        Foo foo = new Foo();
        Bar bar = new Bar();
        bar.name = "foosbar";
        foo.name = "foo";
        foo.bar = bar;
        fooService.write(foo);
        return "Hello from Quarkus REST";
    }
}
