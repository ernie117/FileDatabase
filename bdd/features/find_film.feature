% behave
  Feature: Finding films

    Scenario: Searching for a film by title
        Given the application is running
        When we search for a <film>
        Then it should return <document>

      | film                 | document |
      | the departed         | 0        |
      | the bourne supremacy | 30       |
      | django unchained     | 2        |
