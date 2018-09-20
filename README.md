Eric Ngo, cs8bwaid
February 25, 2018

1)You can use this single command: mkdir barDir; cd barDir; mkdir fooDir
  to create a directory barDir and create another dir fooDir inside of 
  barDir.

2)An example of a wildcard character in use is if I used the command
  ls *.java. *This would list all of the files ending in .java in the 
  terminal.

3)To open all java source code files in a directory into their own tabs,
  you can run the command: gvim -p *.java                   

*4)Static keyword in methods are used so that they can be shared through 
   any instance of the class, and it also doesn't require a calling object.
   It is smart to use a static method when you want to do something, and it
   does not require a calling object, such as the static methods from the
   Math class such as Math.abs(). It is also smart to use static methods
   when you want to access static data.

5) The student can improve her design by first creating an abstract class
   with characteristics shared by all shapes, such as a name. She should
   then create a heirarchy of classes that extend from this abstract class,
   such as creating a class for circle and square. From these two classes,
   she can then create more classes such as red and blue square, which stem
   from the square class, and so on. From this, she will have an organized 
   heirarchy in which she can invoke her program.
