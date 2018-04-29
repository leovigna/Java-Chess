### CIS 120 Homework 9: Chess

## The Chess Game

*   Run GameGUI.

*   The Label displays which player's turn it is, as well as when checkmate occurs.

## Features

*   Regular chess rules.

*   No illegal moves. (Blocked by pieces, King checked, Pinned piece ect...)

*   Kingside & Queenside castle.

## Missing Features & Possible Improvements
*   En-passant rule.

*   Pawn promotion.

*   Draw.

*   Undo, Chess notation

*   File read/write.

## Design Concepts
*   Collections: Sets and Maps used to store map piece locations, sets of moves.

*   Inheritance/Subtyping: All pieces extend the abstract Piece class.

*   Testable: Considerable testing for piece moves, illegal moves, chessmate.

*   Complex game logic: Chess game tracks when king is in check to determine only legal moves, announce checkmate, and handle kigside/queenside castle.
