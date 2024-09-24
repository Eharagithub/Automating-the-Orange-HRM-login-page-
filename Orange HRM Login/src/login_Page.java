import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class login_Page {
        static WebDriver driver; // Declares a static WebDriver variable to control the browser

        //Open Orange HRM login page
        public static void OpenOrangeHRM(){
            driver=new ChromeDriver(); //Creating a new instance of ChromeDriver

            driver.manage().window().maximize(); // Maximize the browser window to full screen

            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45)); // Set the implicit wait time to 45 seconds to allow elements to load before throwing an error
        }

        //Entering valid credentials
        public static void LoginWithValidCredentials(){
            //Test Case 01: Login with Valid Credentials
            // Steps : 1.Open Login page
            //         2.Enter correct username and pw
            //         3.Click on login button
            //         4.Verify that the user is successfully login by checking the presence of the dashboard
            //         5.Log out the application
            //         6.Verify that the user is landing again to the login page

            OpenOrangeHRM();
            driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin"); //enter valid username
            driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123"); //enter valid password
            driver.findElement(By.xpath("//button[@type='submit']")).click(); //click login button
            String ActualMassage= driver.findElement(By.xpath("//h6[text()='Dashboard']")).getText(); //redirect to the dashboard
            String ExpectedMassage="Dashboard"; //give the expected direction

            if(ExpectedMassage.contains(ActualMassage)){ //// Verify if the login was successful
                System.out.println("user login successfuly");
            }else {
                System.out.println("Fail:unsuccesfull login");
            }

            logout();

            WebElement loginPage= driver.findElement(By.xpath("//input[@name='username']")); // Check if the username field is displayed again after logging out
            if(loginPage.isDisplayed()){
                System.out.println("User logout successfully");
            }else{
                System.out.println("User not logout");
            }
        }

        //Entering invalid credentials
        public static void LoginWithInValidCredentials(){
            //Test Case 01: Login with InValid Credentials
            // Steps : 1.Open Login page
            //         2.Enter incorrect username and pw
            //         3.Click on login button
            //         4.Verify that the user is Getting the error message
            //         5.Verify that the user is landing again to the login page
            OpenOrangeHRM();
            driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Upani"); //enter valid username
            driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123"); //enter valid password
            driver.findElement(By.xpath("//button[@type='submit']")).click();


           /*Use WebDriverWait to wait for the error message to appear
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(text(), 'Invalid credentials')]")));*/

            // Check for the error message instead of the dashboard
            WebElement errorMessage = driver.findElement(By.xpath("//p[contains(text(), 'Invalid credentials')]"));
            String actualMessage = errorMessage.getText();
            String expectedMessage = "Invalid credentials";

            // Verify that the error message is displayed
            if (actualMessage.equals(expectedMessage)) {
                System.out.println("Error message displayed correctly: " + actualMessage);
            } else {
                System.out.println("Error message not displayed as expected");
            }
            // Verify that the user is still on the login page by checking if the username field is present
            WebElement loginPage = driver.findElement(By.xpath("//input[@name='username']"));
            if (loginPage.isDisplayed()) {
                System.out.println("User is still on the login page after an invalid login attempt.");
            } else {
                System.out.println("User is not on the login page.");
            }
        }

        //landing to the Logout page
        public static void logout(){

            driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")).click();
            driver.findElement(By.linkText("Logout")).click();  // Select and click the 'Logout' link
        }

        //Main method
        public static void main(String[] args){
            System.setProperty("webdriver.chrome.driver","C:\\Users\\upani\\Downloads\\chromedriver-win64\\chromedriver.exe");

            LoginWithValidCredentials();
            LoginWithInValidCredentials();
        }
    }


