# Model

The users are represented by the User interface, linked to a set of roles, each with their own set of privileges.

![User interface class diagram][user_interface_class_diagram]

A persistent implementation is included for all the objects, and used to store them.

## User Details

These objects are mapped into a Spring's UserDetails by the PersistentUserDetailsService.

[user_interface_class_diagram]: ./images/user_interface_class_diagram.png
