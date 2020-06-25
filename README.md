# Validation-Service
This is a simple API that allow users to validate/standardized their Addresses, Email, phone number

### Address:
- Address validation integrates with third party smarty street to validate their addresses. it is smart enough to know even in the unit levels. For example if you are validating the address on a place that has only 4 floors and you are sending some unit on the 5th floor which does not exist, this service will let you know that it does not exist.

- Other thing to mention is that this service is also using some international countries as well

### phone:
- Another thing that this service does is to reformat a phone number to one way. if you send any valid phone number in any form, the return will be a solid one way standardized way

### Email:
- Emails are also standardized in one solid way.  For instance, emails like in gmail and hotmail that can have + some extra character, will remove the + and extra characters.


# Technology:
- This Api is built on Java Spring 2.0 (Spring Webflux)  - Using 3rd party Integration with Smarty Street- There is no database for this Api because the result get fetch from smarty street and get back to user
