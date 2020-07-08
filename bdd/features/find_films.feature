Feature: Finding films by various means

  Scenario Outline: Searching for a film by title
    Given the application is running
    When we search for a <film>
    Then it should return <id>

    Examples:
      | film                               | id                       |
      | the departed                       | 5ef50257adb9d821bcec4ef0 |
      | django unchained                   | 5ef6561387e7056866953f0a |
      | ghost in the shell                 | 5ef77f56386d2b709bd0b54b |
      | terminator                         | 5ef77f56386d2b709bd0b54c |
      | rise of the planet of the apes     | 5ef77f56386d2b709bd0b54d |
      | gran torino                        | 5ef77f56386d2b709bd0b54e |
      | unforgiven                         | 5ef77f56386d2b709bd0b54f |
      | source code                        | 5ef77f56386d2b709bd0b550 |
      | there will be blood                | 5ef77f56386d2b709bd0b551 |
      | x-men: first class                 | 5ef77f56386d2b709bd0b552 |
      | prisoners                          | 5ef77f56386d2b709bd0b553 |
      | arrival                            | 5ef77f56386d2b709bd0b554 |
      | superman 2                         | 5ef77f56386d2b709bd0b556 |
      | superman 3                         | 5ef77f56386d2b709bd0b557 |
      | superman 4: the quest for peace    | 5ef77f56386d2b709bd0b558 |
      | superman returns                   | 5ef77f56386d2b709bd0b559 |
      | the bourne identity                | 5ef7818a386d2b709bd0b55a |
      | the bourne ultimatum               | 5ef7818a386d2b709bd0b55b |
      | the bourne supremacy               | 5ef7818a386d2b709bd0b55c |
      | looper                             | 5ef627c569b509473e1fa45b |
      | sunshine                           | 5ef6152a69b509473e1fa458 |
      | the girl with the dragon tattoo    | 5ef38169d4af6755bf10c6a8 |
      | ex machina                         | 5ef6268c69b509473e1fa45a |
      | final fantasy vii: advent children | 5ef62badde79ca57cec07b71 |
      | sicario                            | 5ef751d6574fe36db2d203f2 |
      | the fifth element                  | 5ef75523574fe36db2d203f4 |
      | forgetting sarah marshall          | 5ef75523574fe36db2d203f5 |
      | silver linings playbook            | 5ef751d6574fe36db2d203f3 |
      | scott pilgrim vs. the world        | 5ef6561387e7056866953f09 |
      | whiplash                           | 5ef77f56386d2b709bd0b54a |
