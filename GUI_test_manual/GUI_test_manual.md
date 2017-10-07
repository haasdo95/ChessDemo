# GUI Test Manual
## 1. Initial Layout

![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/initial_layout.png "initial_layout")

## 2. Press "Forfeit" Button
- Make a few random moves on the board.
- Press the lower white flag.
- Make sure the board is reset and the score of white gets incremented.
- Vice versa for the upper white flag.

![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/forfeit.png "forfeit")

## 3. Test check visual effect of check
- click on the bug button
![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/check_before.png "check_before")
- move the upper rook the check the white king
![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/check_after.png "check_after")

## 4. Test Checkmate Visual Effect
- click on the bug button
- move the lower rook the check the white king
![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/checkmate.png "checkmate")
- make sure that score gets correctly incremented and the board gets frozen until you hit the start button

## 5. Test stalemate behaviour
- click twice on the bug button
- move the black queen right for one step
![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/stalemate.png "stalemate")
- make sure that the board gets instantly reset, without incrementing any score.

## 6. Test restart behaviour
- make a few random moves; forfeit a few times etc
![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/restart_before.png "rb")
- click on the "restart" button
![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/restart_after.png "ra")
- make sure that after clicking yes, both the board and the score info get reset.

## 7. Test Undo Behaviour
- make a few random moves
- undo them. you should be able to undo to the initial state

## 8. Test "Magic" Behaviour
- click on the magic wand button
![image](/Users/dwd/Documents/Holden's_Codes/cs242/assignment1.2/GUI_test_manual/magic.png "magic")
- make sure that two custom pieces appeared. 
- 炮(Cannon) should be able to jump over exactly one piece to attack
- Khan(the star to the right) would join a random side in the beginning. After killing a piece, it should switch side.
