
# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.




**Class: Assam** <br>
Methods: 
* Assam(String assamString): 
  * To test the string valid we can first test with a valid assamString, e.g."A14N" 
  * Use an invalid assamString to test the length e.g."A14".
  * Use an invalid assamString to test the capital char 'A'. e.g."R14N"
  * Use an invalid assamString to test the coordination of Assam: e.g."R-14N"(the row X is -1)
* getX():
  * Test the number of x, set an invalid x: 8 (x in the rang 0-6).
* getY():
  * Same with getX, set an invalid y:8
* getOrientation():
  * Test the initial position of Assam, valid: "A33S".

**Class: Rug**<br>
Methods:
* Rug(String rugString): 
  * Use a valid rugString to test the validity, e.g."r012324".
  * Use an invalid rugString to test the length: e.g."r0123245".
  * Use an invalid rugString to test the color of the rug: e.g."q012324".
  * Use an invalid rugString to test the id: e.g."r304445".
  * Use an invalid rugString to test the coordination: e.g."c011-234".
* getX1(),getY1(),getX2(),getY2():
  * Same with those in Assam

**Class: Player**<br>
Method:
* Player(String playerString):
  * Use a valid playerString to test the validity, e.g."Pr00803i".
  * Use an invalid playerString to test the length: e.g."Pr00803".
  * Use an invalid playerString to test the first char: e.g."Fr00803i".
  * Use an invalid playerString to test the color of the rug: e.g."Pq00803i".
  * Use an invalid playerString to test the dirhams: e.g."Pr-00803".(the dirham is -008)
  * Use an invalid playerString to test the rug amount: e.g."Pr00803o".


**Class: Player**<br>
Method:
* Player(String playerString):
  * Use a valid playerString to test the validity, e.g."Pr00803i".
  * Use an invalid playerString to test the length: e.g."Pr00803".
  * Use an invalid playerString to test the first char: e.g."Fr00803i".
  * Use an invalid playerString to test the color of the rug: e.g."Pq00803i".
  * Use an invalid playerString to test the dirhams: e.g."Pr-00803".(the dirham is -008)
  * Use an invalid playerString to test the rug amount: e.g."Pr00803o".


**Class: Board**<br>
Methods:
* Board(String boardString):
  * Check the coordination (0,0) and (6,6), if empty then initialize the board.

* parseBoardString(String boardString) 
  * Use an invalid boardString to test the length: e.g."Bn000".
  * Use an invalid boardString to test the first Char 'B': e.g."An00".*   

* getAssamPosition() 
  * Use a valid assamPosition to test the validity, e.g."24N"(coordination+oritation)
 