package services;

import constants.Endpoints;
import io.restassured.response.Response;
import models.request.BookRequest;

public class BookService extends BaseApiService{

    public Response getBook(int bookId) {
        return request()
                .pathParam("id", bookId)
                .when()
                .get(Endpoints.BOOK_BY_ID);
    }

    public Response getAllBooks() {
        return get(Endpoints.BOOKS);
    }

    public Response createBook(BookRequest requestBody) {
        return post(
                Endpoints.BOOKS,
                requestBody
        );
    }

    public Response updateBook(int bookId, BookRequest bookRequest) {

        return request()
                .pathParam("id", bookId)
                .body(bookRequest)
                .when()
                .put(Endpoints.BOOK_BY_ID);
    }

    public Response deleteBook(int bookId) {
        return request()
                .pathParam("id", bookId)
                .when()
                .delete(Endpoints.BOOK_BY_ID);
    }
}
