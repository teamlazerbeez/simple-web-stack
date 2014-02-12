This is the demo app for the techniques described in [these](http://blog.palominolabs.com/2011/08/15/a-simple-java-web-stack-with-guice-jetty-jersey-and-jackson/) [two](http://blog.palominolabs.com/2012/03/31/extending-the-simple-javajettyguicejerseyjackson-web-stack-with-automatic-jersey-resource-method-metrics/) blog posts on a Java web stack with Jetty, Guice, Jersey, and Jackson. While the code does still work, I encourage you to check out the following production ready versions rather than this demo-grade app.

- [jersey-metrics-filter](https://github.com/palominolabs/jersey-metrics-filter), automatic [metrics](http://metrics.codahale.com/) for all [Jersey](https://jersey.java.net/) resource methods
- [jersey-new-relic](https://github.com/palominolabs/jersey-new-relic), Jersey integration into [New Relic](http://newrelic.com/) monitoring
- [jetty-http-server-wrapper](https://github.com/palominolabs/jetty-http-server-wrapper), a simple wrapper around the [Jetty](http://www.eclipse.org/jetty/) http server
- [jersey-cors-filter](https://github.com/palominolabs/jersey-cors-filter) to apply CORS headers to Jersey HTTP responses
- [url-builder](https://github.com/palominolabs/url-builder) to safely build URLs

There's a [demo app](https://github.com/palominolabs/new-relic-sample-app) that shows how to use the above libraries.
