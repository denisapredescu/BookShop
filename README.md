# BookShop

https://docs.google.com/document/d/1DM2_K6RMbRv7PmBai0MUdk9NYFlW_kCtvDij6Wjzn6I/edit?usp=sharing

## Schema

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
- ModelNameServiceTest if the tests are made with MySql Profile <br>
- ModelNameServiceH2Test if the tests are made with H2 Profile
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

</details>


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


- How it looks in *Frontend*