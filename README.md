# BookShop

https://docs.google.com/document/d/1DM2_K6RMbRv7PmBai0MUdk9NYFlW_kCtvDij6Wjzn6I/edit?usp=sharing

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

## Logging
To be able to insert and use logs, the `@Slf4j` annotation is inserted to the h2 test class.

![image](https://github.com/denisapredescu/BookShop/assets/86727047/badb5e62-0a25-4b03-af3d-4f12f3b87200)

The next step is to display the wnated information using the function `log.info(String)`

![image](https://github.com/denisapredescu/BookShop/assets/86727047/3ea51ad5-e569-4aae-a08e-cd87480db112)

The information is display after the test is run in the command prompt

![image](https://github.com/denisapredescu/BookShop/assets/86727047/8271ca2d-813f-4818-a82c-fa6b789d6115)
