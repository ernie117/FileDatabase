Feature: Finding films

  Scenario Outline: Searching for a film by title
      Given the application is running
      When we search for a <film>
      Then it should return <id>

      Examples:
        | film                 | id                       |
        | the departed         | 5ef50257adb9d821bcec4ef0 |
        | the bourne supremacy | 5ef7818a386d2b709bd0b55c |
        | django unchained     | 5ef6561387e7056866953f0a |
