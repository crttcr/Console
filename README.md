# Console


Interfaces and classes to support creating a command line, menu-driven
application to test/explore a library, component, or API.

### Getting Started
At a high level, there are three things that need to happen to create a functioning
text menu driven application

* Subclass ConsoleApp
* Create an action object that does something useful
* Wire that action into a menu item and add it to the menu manager in a setup() override method

Build with the following command:

```
$ gradle build
```
