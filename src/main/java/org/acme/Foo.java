package org.acme;

public class Foo {
    String name;
    Bar bar;
    @Override
    public String toString() {
        return name + "->" + bar.name;
    }
}
