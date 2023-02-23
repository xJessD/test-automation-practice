![Screenshot](/assets/front-end.png "Site Preview")
## Automation Testing Practice

Tools Used:
- Java
- React
- Testng
- Selenium


### Task:
Building upon the initial rundown of TestNG and Selenium we want to:

- clear inputs after submission ✔️
- add a delete button for each account entry, that removes it from the list (use ids) ✔️
- create tests surrounding these new features✔️

Adding on:
- required fields before submission
- email uniqueness
  

 ### Learnings & Reflections:
 
#### `deleteAccount()`test:
The xpath to a delete button is simple (`//p["n"]//button[1]`) as in this case it is a simple website containing no other p tags. `//button[1]` has been used in this case as there are exists a Submit button on the form, and the delete button had only an 'x' as the text. We want to select only the button existing in the appropriate p tag.

```//button[normalize-space()='x']``` in this case would not be as effective it would select all buttons on the screen. 

By using ```//p["n"]```, we can directly target which item on the list we want to click, and test. 

This test generates 5 accounts, and chooses 1 for deletion and testing. Rather than a static item, a random selection allows us to ensure the function works regardless of the number of accounts, and ensures that the test is not just written to pass regardless of functionality. 

Testing checks the number of accounts has has become ```size()-1```, and checks that the selected random n no longer exists. 

To assert this item no longer exists, I've gone with 
```List<WebElement> exists = driver.findElements(By.xpath("//p[@id='"+rdnNum+"']"));```, and asserting the size is 0. Had I gone with a boolean to check, and assert false in the test, this would always fail and throw an error as the element could not be found. 

The xpath ```//p[.//button and contains(text(),'Gmail')]``` was selected so that for testing, we only wanted to count all p tags containing 'Gmail' and a button. I wanted to not count any extra empty form submissions that created empty accounts. This could be solved if there was prior validation on the form, this not needing that check. 

However, the test can be considered skewed as we expected 'Gmail' to be part of the accounts. A fix could be to have appropriate IDs for the list of accounts so that it is easily selectable. 

#### `inputsRequired()`test:
The method I used to test this was ```driver.findElement(By.name("appName")).getAttribute("validationMessage");``` as a way to retreive the error message that appears when attempting to submit a form with an empty & required field. 

Using this though, we are unable select the error message element to check that it is displayed, and this line retrieves the message whether we have submitted the form or not. We can however assert that the validation message is of length > 0 or check it against an expected error message. This validates that an error message exists, but does not verify that it is displayed on the page. 

We can make also this a more thorough check by adding in a test to ensure that nothing was submitted to accounts. That if the user started on a fresh page without any prior accounts, submitting an empty form would not change that. 