jwa
===

This project shows how to add a java agent (jamm) *dynamically* to a web app.

There are two ways to use java agents (statically from the command line and dynamically after the app is running). This web application shows the later.

If you just want to run statically, it's simply a matter of adding -javaagent:path/to/jamm.jar to your web applications JAVA_OPTS path. 

    Also, you must NOT have jamm.jar in your WEB-INF/lib, or anywhere else on your webapp's classpath.

However if you want to add the agent dynamically, it's a bit more troublesome.

JAMM is a native memory allocation size calculator created as a java agent, you can see it here

    https://github.com/jbellis/jamm

Using JAMM dynamically from a web app seems problematic. Perhaps there is an easier way, but this shows that it can be done, albeit painfully.

This is problematic because it appears agents can't be loaded from the classpath, but only from files. So this web app does just this.
When you load a java agent, it is added to the classpath, so our web app will see it (even though it wasn't supplied as part of the war).

That last part is key, DO NOT INCLUDE JAMM.jar in WEB-INF/lib!!.

This app dynamically adds the javaagent in a ServletContextListener.

The location of the jamm jar file is specified in a file in the classpath called jamm.location.

When run, as a test you will see that the MemoryMeter is then allocated, and called correctly outputing a value to the logs.

Unfortunately, at present, the released version of jamm (0.2.6) doesn't allow dynamic installation. So for now, you will have to
build jamm yourself and manually copy it to ${project_loc}/lib.

Another bummer is that in order to connect to the VM at runtime, you need a platform specific tool to do so. For Oracle's JVM at least,
there is a class

    com.sun.tools.attach.VirtualMachine

which allows you to do this. It is found in the tools.jar file, in ${JAVA_HOME}/lib. So this project uses that class. Obviously being a
internal (non-public) class, there are issues there. But it appears that this is the only way to dynamically set up an agent.

To build the war just issue

    ant war

Then to run in Tomcat, create a file under 

    ${TOMCAT_HOME}/conf/Catalina/localhost

called

    jwa.xml

with the contents

    <Context docBase="/home/dave/dev/jwa/war">
    </Context>

(or whereever you placed your project)

Startup Tomcat, and you should see the size of the event object in the logs.




