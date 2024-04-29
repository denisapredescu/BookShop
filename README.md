# BookShop

<p align="center">
  <sub>Project made by <i>Denisa Predescu</i> and <i>Miruna Andreea Postolache</i> for AWBD course, gr 405, 2nd Semester of 1st Year of Databases and Software Tehnologies Master Domain at University of Bucharest, 2024
  </sub>
</p>

## Database schema

<p align="center">
  <img src="Management/MySQLWorkbench.png" alt="MySQLWorkbench" width="70%">
</p>

## Relationship types

<details><summary>Example of `@ManyToMany`,`@OneToMany`,`@ManyToOne`</summary>

<p align="center">
  <img src="Management/BookModel.png" alt="BookModel">
</p>

</details>


<details><summary>Example of `@OneToOne`</summary>

<p align="center">
  <img src="Management/CouponModel.png" alt="CouponModel">
</p>

</details>

## CRUD OPERATIONS

<p align="center">
  <img src="Management/Controller1.png" alt="Controller1">
</p>

<p align="center">
  <img src="Management/Controller2.png" alt="Controller2">
</p>

<p align="center">
  <img src="Management/Controller3.png" alt="Controller3">
</p>


## Switching between profiles 
If you set the active profile to MySQL (mysql), the H2 tests will not fail by default, but they won't run either. The tests annotated with @ActiveProfiles("h2") will be ignored because the active profile is set to MySQL.

To run both MySQL and H2 tests without failing, you need to specify the active profile when running your tests, and ensure that the tests are properly configured to use the corresponding database profile. For example, *if you want to run MySQL tests, you would set the active profile to mysql*. Conversely, *if you want to run H2 tests, you would set the active profile to h2.*

Here's how you can run tests for each profile:

<i>MySQL Tests:</i><br>
Set the active profile to MySQL (mysql).<br>
Run your tests.

<i>H2 Tests:</i><br>
Set the active profile to H2 (h2).<br>
Run your tests.

![image](https://github.com/denisapredescu/BookShop/assets/86727047/e8ea4f5b-6dce-4f4d-b44a-373ae49a2724)

## Repository tests (mysql and h2)

<details><summary><b>Present all the tests <br>
The classes are named after the following logic: <br>
- ModelNameRepositoryTest if the tests are made with MySql Profile <br>
- ModelNameRepositoryH2Test if the tests are made with H2 Profile
</b></summary>

<p align="center">
  <img src="Management/RepositoryTests.png" alt="RepositoryTests">
</p>

</details>

## Unit-tests

<details><summary><b>Present all the tests on services</b></summary>

<div style="display: flex;">
  <img src="Management/ServiceTest1.png" alt="ServiceTest1" width="50%">
  <img src="Management/ServiceTest2.png" alt="ServiceTest2" width="50%">
</div>

</details>

<details><summary><b>Present all the tests on controller</b></summary>
<div style="display: flex;">
  <img src="Management/ControllerTest1.png" alt="ControllerTest1" width="50%">
  <img src="Management/ControllerTest2.png" alt="ControllerTest2" width="50%">
</div>
</details>

## Forms data validation
- The Model

The annotations like @NotNull, @Min, @Positive will be validated when the object will be
used in the same time with @Valid annotation in functions.

<p align="center">
    <img src="Management/Model_FormsAndDataValidation.png" alt="Model_FormsAndDataValidation">
</p>

- How it looks in *Controller*

We need to use BindingResult to detect errors caused by not filling fields correctly.
Also after @ModelAttribute annotation we need to specify the object that it is return
by the form with the given name from the .html file.
<p align="center">
    <img src="Management/Controller_FormsDataValidation.png" alt="Controller_FormsDataValidation">
</p>

- How it looks in *Frontend*
<p align="center">
    <img src="Management/FormsDataValidation.png" alt="FormsDataValidation" width="50%">
</p>

### Exception custom pages
When the user has no books in the basket and he/she click on sent button from the "My Basket" page 
the "NoFoundElementException" is caught and a custom error page will appear on the screen.
<p align="center">
    <img src="Management/NoFoundElementException.png" alt="Controller_PaginateAndSort">
</p>
<p align="center">
    <img src="Management/handleNoSuchElementException.png" alt="Controller_PaginateAndSort">
</p>

- How it looks the custom page
<p align="center">
    <img src="Management/Exception_noBooks.png" alt="Controller_PaginateAndSort" width="50%">
</p>

- Another example is when on register a user entered an email that already exists

<p align="center">
    <img src="Management/EmailAlreadyUsedException.png" alt="EmailAlreadyExists">
</p>
<p align="center">
    <img src="Management/handleEmailAlreadyUsedException.png" alt="handleEmailAlreadyUsedException">
</p>

<p align="center">
    <img src="Management/EmailAlreadyExists.png" alt="EmailAlreadyExists" width="50%">
</p>

## Logging
To be able to insert and use logs, the `@Slf4j` annotation is inserted to the h2 test class.

![image](https://github.com/denisapredescu/BookShop/assets/86727047/badb5e62-0a25-4b03-af3d-4f12f3b87200)

The next step is to display the wnated information using the function `log.info(String)`

![image](https://github.com/denisapredescu/BookShop/assets/86727047/3ea51ad5-e569-4aae-a08e-cd87480db112)

The information is display after the test is run in the command prompt

![image](https://github.com/denisapredescu/BookShop/assets/86727047/8271ca2d-813f-4818-a82c-fa6b789d6115)

## Paginate and Sorting 

- How it looks in *Repository*

<p align="center">
    <img src="Management/Repository_paginate.png" alt="Repository_paginate">
</p>

- How it looks in *Service* 
<p align="center">
    <img src="Management/Service_PaginateAndSort.png" alt="Service_PaginateAndSort">
</p>

- How it looks in *Controller* 
<p align="center">
    <img src="Management/Controller_PaginateAndSort.png" alt="Controller_PaginateAndSort">
</p>

- How it looks in *Frontend*
<p align="center">
    <img src="Management/Frontend_PagingAndSorting.png" alt="Controller_PaginateAndSort">
</p>

## Spring Security - JDBC Authentication
<details><summary><b>Security, Role Permissions</b></summary>

<p align="center">
    <img src="Management/SpringSecurity.png" alt="SpringSecurity">
</p>
</details>

<details><summary><b>Login Form</b></summary>

<p align="center">
    <img src="Management/LoginForm.png" alt="LoginForm">
</p>
</details>
