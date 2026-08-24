Feature: Book API

  #Create
  @positive
  Scenario: Create a new book
    Given I prepare a new book with Title "Crime and Punishment" , Author "Fyodor Dostoevsky" , pages 671 and Published Year 2002
    When I send a request to create the book
    Then the response status code should be 201
    And response message should be "Book created successfully"
    And the book should be created successfully

    #Create - Negative Scenarios
  @negative
  Scenario Outline: Create a new book (Negative Scenarios)
    Given I prepare a new book with Title "<title>" , Author "<author>" , pages <pages> and Published Year <publishedYear>
    When I send a request to create the book
    Then the response status code should be 400
    And response message should be "<errorMessage>"
      Examples:
        | title                | author            | pages | publishedYear | errorMessage                            |
        |                      | Fyodor Dostoevsky | 200   | 2002          | Title is required                       |
        | Crime and Punishment |                   | 200   | 2002          | Author is required                      |
        | Crime and Punishment | Fyodor Dostoevsky | 0     | 2002          | Pages should be greater than 0          |
        | Crime and Punishment | Fyodor Dostoevsky | 200   | -500          | Published Year should be greater than 0 |


  #Update (PUT)
  @positive
  Scenario: Update only the book title
    Given I want to update the book titled "Crime and Punishment" to be "New Title"
    Then the response status code should be 200
    And response message should be "Book updated successfully"
    And the book title should be updated successfully and other data should still be the same

  #Update (PUT) - Negative Scenario
  @negative
    Scenario: Update only the book title
    Given I want to update the book titled "Crime and Punishment" to be "<html>"
    Then the response status code should be 400
    And response message should be "Book Title contains incorrect data"


  #Delete
  @positive
  Scenario: Delete book by id
    Given I want to delete the book with id = 100
    Then the response status code should be 200
    And response message should be "Book deleted successfully"
    And the deleted book shouldn't exist anymore

    #Delete - Negative Scenario
  @negative
  Scenario: Delete book by id
    Given I want to delete the book with id = 479898230420842
    Then the response status code should be 400
    And response message should be "Book id doesn't exist"
