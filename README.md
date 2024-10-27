# quarkus-kcryo-classloader-problem

## Problem

Quarkus is failing reading a kryo generated file at `@Startup` event.

## Reproduce

To reproduce run `quarkus build` to execute the test which generates a foo.bin file.
Then run `quarkus dev` you should see this exception:

```
: java.lang.RuntimeException: Failed to start quarkus
        at io.quarkus.runner.ApplicationImpl.doStart(Unknown Source)
        at io.quarkus.runtime.Application.start(Application.java:101)
        at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:119)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
        at io.quarkus.runner.GeneratedMain.main(Unknown Source)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
        at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:116)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: com.esotericsoftware.kryo.kryo5.KryoException: Unable to find class: org.acme.Bar
Serialization trace:
bar (org.acme.Foo)
        at com.esotericsoftware.kryo.kryo5.util.DefaultClassResolver.readName(DefaultClassResolver.java:182)
        at com.esotericsoftware.kryo.kryo5.util.DefaultClassResolver.readClass(DefaultClassResolver.java:151)
        at com.esotericsoftware.kryo.kryo5.Kryo.readClass(Kryo.java:758)
        at com.esotericsoftware.kryo.kryo5.serializers.ReflectField.read(ReflectField.java:117)
        at com.esotericsoftware.kryo.kryo5.serializers.FieldSerializer.read(FieldSerializer.java:130)
        at com.esotericsoftware.kryo.kryo5.Kryo.readObject(Kryo.java:774)
        at org.acme.FooService.read(FooService.java:41)
        at org.acme.FooService_ClientProxy.read(Unknown Source)
        at org.acme.FooService_Observer_Synthetic_uGqxCcFAHeW_cbd7cwiiNgG47Ic.notify(Unknown Source)
        at io.quarkus.arc.impl.EventImpl$Notifier.notifyObservers(EventImpl.java:351)
        at io.quarkus.arc.impl.EventImpl$Notifier.notify(EventImpl.java:333)
        at io.quarkus.arc.impl.EventImpl.fire(EventImpl.java:80)
        at io.quarkus.arc.runtime.ArcRecorder.fireLifecycleEvent(ArcRecorder.java:156)
        at io.quarkus.arc.runtime.ArcRecorder.handleLifecycleEvents(ArcRecorder.java:107)
        at io.quarkus.deployment.steps.LifecycleEventsBuildStep$startupEvent1144526294.deploy_0(Unknown Source)
        at io.quarkus.deployment.steps.LifecycleEventsBuildStep$startupEvent1144526294.deploy(Unknown Source)
        ... 11 more
Caused by: java.lang.ClassNotFoundException: org.acme.Bar
        at io.quarkus.bootstrap.classloading.QuarkusClassLoader.loadClass(QuarkusClassLoader.java:506)
        at io.quarkus.bootstrap.classloading.QuarkusClassLoader.loadClass(QuarkusClassLoader.java:481)
        at java.base/java.lang.Class.forName0(Native Method)
        at java.base/java.lang.Class.forName(Class.java:534)
        at java.base/java.lang.Class.forName(Class.java:513)
        at com.esotericsoftware.kryo.kryo5.util.DefaultClassResolver.readName(DefaultClassResolver.java:176)
        ... 26 more
```
