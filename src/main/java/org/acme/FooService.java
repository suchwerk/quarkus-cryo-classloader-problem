package org.acme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.KryoException;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FooService {

    static File file = new File("foo.bin");

    public void write(Foo foo) throws Exception {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setReferences(true);

        try (Output output = new Output(new FileOutputStream(file))) {
            kryo.writeObject(output, foo);
            output.close();
        } catch (FileNotFoundException | KryoException e) {
            throw e;
        }
    }

    @Startup
    public void read() throws Exception {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setReferences(true);
        if (file.exists()) {
            try (Input input = new Input(new FileInputStream(file))) {
                Foo foo = kryo.readObject(input, Foo.class);
                System.out.println(foo);
            } catch (FileNotFoundException | KryoException e) {
                throw e;
            }
        } else {
            System.err.println("Please generate: " + file.getAbsolutePath());
        }
    }

}
