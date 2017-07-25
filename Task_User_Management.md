# User Account Management

### Sign in
* Google
* Facebook

### Guidlines
***
* Fork this Repository to your Account
* Create a baranch and all the time, Your work should be in that branch

### Task
***
Your Task is to Create an Android Library through which an user can sign in to the App.

App Should log the following events for Analytics

* Searches for a keyword
* Clicks on an offer 
* opened a notification
* Noification Actions 
  * take me there
  * not interested 


Library should provide a ***Static Method*** to Log an event from any Context.

### Search Functionality

* When user Searches for a product, App queries the database for matching Offers.
* Returns a ListView with Results sorted accordinf to users preference
  * Location
  * Price
  * Rating




#### Input  :
Keywords

#### Output :
An ListView which displays list of search results.


> ### Important
>
>* The Library should not depend on App's Resources
>* The Library should handle requests in its own background thread and Return results via an Interface.

