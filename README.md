jwa
===

A web application showing to add the jamm java agent to a web app


Using JAMM from a web app seems problematic. Perhaps there is an easier way, but this shows that it can be done, albeit painful.
From what i surmise you can't specify -javaagent in JAVA_OPTS because the command line argument requires a file reference, but
the web application uses jamm from a classpath resource. Thus you are using two distinct jars, and the version in the web app
doesn't know that it has been initialized (as it hasn't).

The solution is to dynamically add the agent at runtime.

This is still problematic, as it appears agents can't be loaded from the classpath, but only from files. So this web app does just this.

It dynamically adds the javaagent in a ServletContextListener.

The location of the jamm jar file is specified in a file in the classpath called jamm.location.

When run, you will see that the MemoryMeter is then allocated, and called correctly.

Unfortunately, at present, the released version of jamm (0.2.6) doesn't allow dynamic installation. So for now, you will have to
build jamm yourself and manually copy it to {project_loc}/lib.


