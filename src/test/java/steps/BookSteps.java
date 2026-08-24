package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.request.BookRequest;
import models.response.BookResponse;
import org.testng.Assert;
import services.BookService;

import java.util.List;

public class BookSteps  {

    private final BookService bookService;

    private BookRequest createBookRequest;
    private BookRequest updateBookRequest;
    private Response response;
    private BookResponse bookResponse;

    private BookResponse existingBook;
    private BookResponse updatedBook;

    private int deletedBookId;

    public BookSteps() {
        this.bookService = new BookService();
    }

    @Given("I prepare a new book with Title {string} , Author {string} , pages {int} and Published Year {int}")
    public void prepareBook(String title, String author, int pages, int publishedYear) {
        createBookRequest = new BookRequest(title, author, pages, publishedYear);
    }

    @When("I send a request to create the book")
    public void createBook() {
        response = bookService.createBook(createBookRequest);
        bookResponse = response.as(BookResponse.class);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.statusCode(), expectedStatusCode,"Status code is incorrect");;
    }

    @And("response message should be {string}")
    public void verifyResponseMessage(String message) {
        Assert.assertEquals(bookResponse.getMessage(), message, "Unexpected response message");
    }

    @And("the book should be created successfully")
    public void verifyBookData() {
        Assert.assertEquals(bookResponse.getTitle(), createBookRequest.getTitle(),"Title is incorrect");
        Assert.assertEquals(bookResponse.getAuthor(), createBookRequest.getAuthor(),"Author is incorrect");
        Assert.assertEquals(bookResponse.getPages(), createBookRequest.getPages(),"Pages are incorrect");
        Assert.assertEquals(bookResponse.getPublishedYear(), createBookRequest.getPublishedYear(),"Published Year is incorrect");
    }

    @Given("I want to update the book titled {string} to be {string}")
    public void updateBookTitle( String currentTitle, String newTitle) {
        Response getResponse = bookService.getAllBooks();

        // Get All books list
        List<BookResponse> books = getResponse.jsonPath().getList("", BookResponse.class);

        // Filter by given Title
        BookResponse existingBook =
                books.stream()
                        .filter(book ->
                                currentTitle.equals(book.getTitle()))
                        .findFirst()
                        .orElseThrow(() -> new AssertionError(
                                        "Book not found with title: " + currentTitle));

        updateBookRequest = new BookRequest(newTitle, existingBook.getAuthor(), existingBook.getPages(), existingBook.getPublishedYear());
        response = bookService.updateBook(existingBook.getId(), updateBookRequest);
        bookResponse = response.as(BookResponse.class);
    }

    @And("the book title should be updated successfully and other data should still be the same")
    public void verifyUpdateTitleData() {
        Assert.assertEquals(bookResponse.getTitle(), updateBookRequest.getTitle(), "Book title was not updated correctly");
        Assert.assertEquals(updatedBook.getAuthor(), existingBook.getAuthor(), "Author was changed unexpectedly");
        Assert.assertEquals(updatedBook.getPages(), existingBook.getPages(), "Number of pages was changed unexpectedly");
        Assert.assertEquals(updatedBook.getPublishedYear(), existingBook.getPublishedYear(), "Published year was changed unexpectedly");
    }


    @Given("I want to delete the book with id = {int}")
    public void deleteBook(int bookId) {
        deletedBookId = bookId;
        response = bookService.deleteBook(bookId);
    }

    @And("the deleted book shouldn't exist anymore")
    public void verifyDeletedBook() {
        Response getResponse = bookService.getBook(deletedBookId);
        Assert.assertEquals(getResponse.statusCode(), 400, "Book id doesn't exist");
    }
}
