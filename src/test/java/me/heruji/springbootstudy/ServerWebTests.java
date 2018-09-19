package me.heruji.springbootstudy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ServerWebTests {
    @Autowired
    private WebDriver webDriver;

    @Test
    public void addBookToEmptyList() {
        webDriver.get("/readingList");
        webDriver.findElement(By.name("username")).sendKeys("admin");
        webDriver.findElement(By.name("password")).sendKeys("123");
        webDriver.findElement(By.tagName("form")).submit();

        assertEquals("You have no books in your book list",
                webDriver.findElement(By.tagName("div")).getText());
        webDriver.findElement(By.name("title")).sendKeys("BOOK TITLE");
        webDriver.findElement(By.name("author")).sendKeys("BOOK AUTHOR");
        webDriver.findElement(By.name("isbn")).sendKeys("1234567890");
        webDriver.findElement(By.name("description")).sendKeys("DESCRIPTION");
        webDriver.findElement(By.tagName("form")).submit();

        WebElement dl = webDriver.findElement(By.cssSelector("dt.bookHeadline"));
        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890) BOOK TITLE", dl.getText());
        WebElement dt = webDriver.findElement(By.cssSelector("dd.bookDescription"));
        assertEquals("DESCRIPTION", dt.getText());
    }
}
