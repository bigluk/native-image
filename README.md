# native-image

## Indice

1) Microservices Architecture and Cloud
2) Cloud and serverless computing
3) Serveless characteristics
4) Springboot framework and serverless Lambda Aws
5) GraalVM and Native Image
6) Practical Example
7) Conclusion


## Microservices Architecture and Cloud
The field of software engineering has been shaped by continuous evolution in response to new problems and requirements. This progression has led to the development of various software architecture designs to meet the needs of different system characteristics and challenges over time.
The history of software architecture design traces its roots back to the early days of programming, when software systems were relatively simple and created for very specific tasks. Over time, the increase in complexity and the need for scalable, maintainable, and flexible systems have led to the emergence of numerous software architecture styles.

### Monolithic Architecture
In the early stages of software development, a monolithic architecture was the most common approach. Monolithic architectures represent a single-tiered, tightly coupled, and self-contained software system, where all components, such as user interface, business logic, and data access, are executed within a single process. This design style is characterized by simplicity and allows for efficient code execution. Still, as software systems grew in complexity, the limitations of monolithic architectures became evident. Monolithic architectures proved difficult to maintain, scale, and evolve.
To overcome these challenges, a new architectural style called Microservice Architecture emerged as a solution.

### Microservice Architecture
Microservices architecture is an advanced approach to software development that seeks to address the limitations of monolithic architecture. In microservices architecture, an application is structured as a collection of small, independent services that are loosely coupled and can be developed, deployed, and scaled independently of one another. Each service typically has its own codebase, storage, and deployment pipeline, which allows for a high degree of flexibility and autonomy in the development process.
One of the main benefits of microservices architecture is improved scalability. As each service can be scaled independently, teams can better manage resources and costs by scaling only the services that require additional capacity. This also allows for a more efficient use of hardware and cloud resources, as under-utilized services can be downscaled when not in demand.
Another advantage of using microservices is their fault tolerance. When an individual service fails, it does not necessarily bring down the entire application, as other services can continue to operate independently. This resilience makes microservices-based applications more reliable and less prone to downtime.
Microservices architecture also supports better organization and management of development teams. Due to the separation of concerns and responsibilities, teams can be divided along the lines of the services they maintain, allowing them to work autonomously and focus on specific application areas. This enables faster development cycles, as multiple teams can work in parallel without causing bottlenecks due to interdependency.
The flexibility of microservices architecture also brings technology diversity to the table. Since each service can use different technologies, teams can choose the most suitable tools and frameworks for the task at hand. This can result in a more efficient and performant software solution.
Due to the concepts of high scalability, fault tollerance, flexibility and faster development cycles, the microservice architecture finds in cloud computing a faithful ally.



## Cloud and serverless computing
Strictly speaking, cloud computing is the on-demand availability of computing resources (such as storage and infrastructure), as services over the internet. It eliminates the need for individuals and businesses to self-manage physical resources themselves. The main cloud computing service models include infrastructure as a service offers compute and storage services, platform as a service offers a develop-and-deploy environment to build cloud apps, and software as a service delivers apps as services. One of the many advantages of cloud computing is that you only pay for what you use. This allows organizations to scale faster and more efficiently without the burden of having to buy and maintain their own physical data centers and servers.
One of the main branch born in the last year concerning the cloud computing is the concept of serverless service. A serverless service, that is key topic of this paper, is a cloud computing execution model that allocates machine resources on an as-used basis. Under a serverless model, developers can build and run applications without having to manage any servers and pay only for the exact amount of resources used. Instead, the cloud service provider is responsible for provisioning, managing, and scaling the cloud infrastructure that runs the application code.
While the name can be misleading, serverless does not mean “no servers.” Instead, serverless apps abstract away the routine infrastructure work associated with application development. You have no visibility into the machines that run your applications, can’t configure them, and don’t have to manage or scale them. In other words, you pay for the service of a server, not the server itself. 
From the development perspective, it’s as if there are no servers at all—developers write the code, deploy it to production, and the cloud provider handles the rest.



## Serveless characteristics
We can describe serverless computing in few words:
* On demand: the application deployed on servelless service will not run continuously but it will be invoked only when a request will call it and will be terminated when the request will be completely processed.
* Short time to process a request: service computing can process a request for a fixed short time, for example, the AWS lambda can process a request for 900 seconds at most.
* Price: the cost related to serverless computing is calculated based on the real use of the service, the ram allocated, number of request etc...



## Springboot framework and serverless Lambda Aws
So, after describing the main characteristics of the serverless computing, a question raise spontaneously... what if we want to run a springboot application on a serverless service like Lambda AWS?
Spring boot is the most famous java framework for backend development. it is very rich in features that allow us to create complex software with simplicity. However, having all these features has a cost: long start-up times and significant use of RAM which makes applications made with Springboot unsuitable for being released on serverless services for via their characteristics seen above. That's the reason that pushed Oracle, the company that owns the Java language, to develop a tool, called GraalVM, to deal with this problem.



## GraalVM and Native Image
GraalVM started in 2011 as a research project at Oracle Labs to create a high-performance JDK that can speed up the performance of Java and JVM-based applications using an alternative just-in-time (JIT) compiler. It lowers application latency, improves peak throughput by reducing garbage collection time, and comes with 24/7 Oracle support.
There is also a native image utility that compiles Java bytecode ahead-of-time (AOT) and generates native executables for some applications that start up almost instantaneously and use very little memory resources.

###  How GraalVM works
So, the Graal compiler also works as an ahead-of-time (AOT) compiler, producing native executables. Given Java's dynamic nature, how does that work exactly?
Unlike JIT mode, where compilation and execution happen at the same time, in AOT mode the compiler performs all compilations during build time, before the execution. The main idea here is to move all the "heavy lifting" — expensive computations — to build time, so it can be done once, and then at runtime generated executables start fast and are ready from the get-go because everything is pre-computed and pre-compiled.
The GraalVM 'native-image' utility takes Java bytecode as input and outputs a native executable. To do so, the utility performs a static analysis of the bytecode under a closed world assumption. During the analysis, the utility looks for all the code that your application actually uses and eliminates everything that is unnecessary.

These three key concepts help you better understand the Native Image generation process:
1) Points-to analysis. GraalVM Native Image determines which Java classes, methods, and fields are reachable at runtime, and only those will be included in the native executable. The points-to analysis starts with all entry points, usually the main method of the application. The analysis iteratively processes all transitively reachable code paths until a fixed point is reached and the analysis ends. This applies not only to the application code but also to the libraries and JDK classes — everything that is needed for packaging an application into a self-contained binary.
2) Initializations at build time. GraalVM Native Image defaults to class initialization at runtime to ensure correct behavior. But if Native Image can prove that certain classes are safe to initialize, it will initialize them at build time instead. This makes runtime initialization and checks unnecessary and improves performance.
3) Heap snapshotting. Heap snapshotting in Native Image is a very interesting concept and deserves its own article. During the image build process, Java objects allocated by static initializers, and all the objects that are reachable, are written onto the image heap. This means that your application starts much faster with a pre-populated heap.
What's interesting is that points-to analysis makes objects reachable in the image heap, and the snapshotting that builds the image heap can make new methods reachable for the points-to analysis. Thus, points-to analysis and heap snapshotting are performed iteratively until a fixed point is reached.
The following image shows what is described in the previous three points.

![image](https://github.com/bigluk/native-image/assets/133915457/2f557e3b-f4ae-4d51-a972-e620cb4dcbcb)

### Native Image Build Process
After the analysis is complete, Graal compiles all the reachable code into a platform-specific native executable. That executable is fully functional on its own and doesn't need the JVM to run. As a result, you get a slim and fast native executable version of your Java application: one that performs the exact same functions but contains only the necessary code and its required dependencies.
But who takes care of features such as memory management and thread scheduling in the native executable? For that, Native Image includes Substrate VM — a slim VM implementation that provides runtime components, such as a garbage collector and a thread scheduler. Just like the Graal compiler, Substrate VM is written in the Java programming language and AOT-compiled by GraalVM Native Image into native code!



## Practical Example
After all this theory, let's get some practical example... The code used for the practice is the one on this GitHub repo. It was built a simple rest API with 4 endpoint for the CRUD operation and 1 "extra" endpoint used just to demostrate how to deal with reflection with native image.
The technological stack used is springboot 3.2.1, java 17, maven and h2 as db.
We have tried to use as much dependency and functionality as possibile to demonstrate how native is largely integrated with a lot of dependencies.
Let's take a look at the results achieved:

### Start up Time
Let's start with the startup time. As you can see in the following image we have achieved a great result, reducing the startup time of 20 times!

![Screenshot 2024-01-16 172507](https://github.com/bigluk/native-image/assets/133915457/51bb35a0-1194-42d7-81e0-1a15e21a2c3f)

### RAM
Let's continue studying the RAM consumption, the following image demonstrate how the RAM consumption is decreased of 60%!

![Screenshot 2024-01-16 173142](https://github.com/bigluk/native-image/assets/133915457/08bd44ca-5938-4355-973d-c317bbeba4be)

The last but not the least, let me show you a price comparison between the use of native executable and classic executable with Lambda AWS. The price showed in the next image are calculated using the online service called AWS calculator. The setting used for the stimation are: x86 lambda architecture, Milan Region, 1 milion request per month with 10 seconds of processing time on average, 512 MB of storage and 1000 MB for native executable and 1600 MB (1000 + 60%) obtained from the first point. Let's take a look at the image:

![Screenshot 2024-01-16 173359](https://github.com/bigluk/native-image/assets/133915457/f2b851c4-c6b2-472b-82a2-7678083dda9f)


As we can see we reduced the cost of the lambda service considerably!

### Dealing with Java Reflection
Another aspect that we want to discuss is the Reflection problem. Reflection is one of the most used, very useful and important feature of java language. It allows an executing Java program to examine or "introspect" upon itself, and manipulate internal properties of the program. For example, it's possible for a Java class to obtain the names of all its members and display them. While in JIT compilation the reflection is resolved in runtime, in AOT compilation is resolved during build time to improve performance, to make to this works, we need to configure a json configuration file. Same story if we want to use dynamic proxies and other java feature.



## Conclusion
This short paper as the goal to demonstrate how performance and cost improve using the new native image technology with framework such as springboot in a serverless service such as Lambda AWS. Obviously, as discussed in the Reflection part, in certain case, we have to deal with configuration and compatibility problem, in case we use old version of dependency or framework (springboot < 3.0.0), but in most cases the gain is obvious!

Thanks for reading!


