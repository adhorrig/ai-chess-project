/*

Details:
----------
Name: Adam Horrigan.
Student Number: x13735825.
Class: BSc(Hons) in Computing - Data Analytics.
Date: 20/10/16.
*/


/*

Work done:
-------------
1) Valid movements for all pieces.
2) White pawn issues fixed.
3) Game is turn based.
4) End game has been implemented.

*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
  JLayeredPane layeredPane;
  JPanel chessBoard;
  JLabel chessPiece;
  int xAdjustment;
  int yAdjustment;
  int startX;
  int startY;
  int initialX;
  int initialY;
  JPanel panels;
  JLabel pieces;
  String turn = "White";
  int xLeft;
  int xRight;
  int yUp;
  int yDown;


  public ChessProject(){
    Dimension boardSize = new Dimension(600, 600);

    //  Use a Layered Pane for this application
    layeredPane = new JLayeredPane();
    getContentPane().add(layeredPane);
    layeredPane.setPreferredSize(boardSize);
    layeredPane.addMouseListener(this);
    layeredPane.addMouseMotionListener(this);

    //Add a chess board to the Layered Pane
    chessBoard = new JPanel();
    layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
    chessBoard.setLayout( new GridLayout(8, 8) );
    chessBoard.setPreferredSize( boardSize );
    chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

    for (int i = 0; i < 64; i++) {
      JPanel square = new JPanel( new BorderLayout() );
      chessBoard.add( square );
      int row = (i / 8) % 2;
      if (row == 0)
        square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
      else
        square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
    }

    // Setting up the Initial Chess board.

    for(int i=8;i < 16; i++){
      pieces = new JLabel( new ImageIcon("WhitePawn.png") );
      panels = (JPanel)chessBoard.getComponent(i);
      panels.add(pieces);
    }
  	pieces = new JLabel( new ImageIcon("WhiteRook.png") );
  	panels = (JPanel)chessBoard.getComponent(0);
    panels.add(pieces);

  	pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
  	panels = (JPanel)chessBoard.getComponent(1);
    panels.add(pieces);

  	pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
  	panels = (JPanel)chessBoard.getComponent(6);
    panels.add(pieces);

  	pieces = new JLabel( new ImageIcon("WhiteBishop.png") );
  	panels = (JPanel)chessBoard.getComponent(2);
    panels.add(pieces);

  	pieces = new JLabel( new ImageIcon("WhiteBishop.png") );
  	panels = (JPanel)chessBoard.getComponent(5);
    panels.add(pieces);

  	pieces = new JLabel( new ImageIcon("WhiteKing.png") );
  	panels = (JPanel)chessBoard.getComponent(3);
    panels.add(pieces);

  	pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
  	panels = (JPanel)chessBoard.getComponent(4);
    panels.add(pieces);

  	pieces = new JLabel( new ImageIcon("WhiteRook.png") );
  	panels = (JPanel)chessBoard.getComponent(7);
    panels.add(pieces);

    for(int i=48;i < 56; i++){
      pieces = new JLabel( new ImageIcon("BlackPawn.png") );
      panels = (JPanel)chessBoard.getComponent(i);
      panels.add(pieces);
    }
    pieces = new JLabel( new ImageIcon("BlackRook.png") );
    panels = (JPanel)chessBoard.getComponent(56);
    panels.add(pieces);

    pieces = new JLabel( new ImageIcon("BlackKnight.png") );
    panels = (JPanel)chessBoard.getComponent(57);
    panels.add(pieces);

    pieces = new JLabel( new ImageIcon("BlackKnight.png") );
    panels = (JPanel)chessBoard.getComponent(62);
    panels.add(pieces);

    pieces = new JLabel( new ImageIcon("BlackBishop.png") );
    panels = (JPanel)chessBoard.getComponent(58);
    panels.add(pieces);

    pieces = new JLabel( new ImageIcon("BlackBishop.png") );
    panels = (JPanel)chessBoard.getComponent(61);
    panels.add(pieces);

    pieces = new JLabel( new ImageIcon("BlackKing.png") );
    panels = (JPanel)chessBoard.getComponent(59);
    panels.add(pieces);

    pieces = new JLabel( new ImageIcon("BlackQueen.png") );
    panels = (JPanel)chessBoard.getComponent(60);
    panels.add(pieces);

    pieces = new JLabel( new ImageIcon("BlackRook.png") );
    panels = (JPanel)chessBoard.getComponent(63);
    panels.add(pieces);
  }

  //Check if a piece is present is on a given X,Y coordinate

  private Boolean piecePresent(int x, int y){
    Component c = chessBoard.findComponentAt(x, y);
    if(c instanceof JPanel){
      return false;
    }
    else{
      return true;
    }
  }

  //Check if the piece being taken is an opponent of white

  private Boolean checkWhiteOponent(int newX, int newY){
    Boolean oponent;
    Component c1 = chessBoard.findComponentAt(newX, newY);
    JLabel awaitingPiece = (JLabel)c1;
    String tmp1 = awaitingPiece.getIcon().toString();
    if(((tmp1.contains("Black")))){
      oponent = true;
    } else {
      oponent = false;
    }
    return oponent;
  }

  //Check if the piece being taken is an opponent of black

  private boolean checkBlackOponent(int newX, int newY){
    Boolean oponent;
    Component c1 = chessBoard.findComponentAt(newX, newY);
    JLabel awaitingPiece = (JLabel)c1;
    String tmp1 = awaitingPiece.getIcon().toString();
    if(((tmp1.contains("White")))){
      oponent = true;
    } else {
      oponent = false;
    }
    return oponent;
  }

  //Check if there is a king present on an X,Y coordinate

  private boolean kingPiece(int x, int y){
    if(piecePresent(x,y)){
      Component c1 = chessBoard.findComponentAt(x,y);
      JLabel awaitingPiece = (JLabel)c1;
      String tmp1 = awaitingPiece.getIcon().toString();
      if(((tmp1.contains("King")))){
        return true;
      }
    }
    return false;
  }

  //Check the surrounding pieces of a square

  private boolean surroundingPiecesCheck(MouseEvent e) {
    xLeft = e.getX() - 75;
    xRight = e.getX() + 75;
    yDown = e.getY() - 75;
    yUp = e.getY() + 75;
    initialX = e.getX();
    initialY = e.getY();

    return kingPiece(xLeft, yDown)
            || kingPiece(xLeft, initialY)
            || kingPiece(xLeft, yUp)
            || kingPiece(xRight, yDown)
            || kingPiece(initialX, yDown)
            || kingPiece(xRight, initialY)
            || kingPiece(xRight, yUp)
            || kingPiece(initialX, yUp);
  }

  //Check if a king has been taken so the game can end

  private void winningMoveCheck(int x, int y) {
    if (piecePresent(x,y)) {
      Component c1 = chessBoard.findComponentAt(x, y);
      JLabel awaitingPiece = (JLabel) c1;
      String tmp1 = awaitingPiece.getIcon().toString();
      if (tmp1.contains("King")) {
        JOptionPane.showMessageDialog(null, this.turn + " Player wins!");
        System.exit(0);
      }
    }
  }

  //Check if the mouse has been pressed on the board

  public void mousePressed(MouseEvent e){
    chessPiece = null;
    Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
    if (c instanceof JPanel)
      return;

    Point parentLocation = c.getParent().getLocation();
    xAdjustment = parentLocation.x - e.getX();
    yAdjustment = parentLocation.y - e.getY();
    chessPiece = (JLabel)c;
    initialX = e.getX();
    initialY = e.getY();
    startX = (e.getX()/75);
    startY = (e.getY()/75);
    chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
    chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
    layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
  }

  //Check which players turn it is (Black or White)

  public Boolean isPlayersTurn(String pieceName) {
    if (pieceName.contains("White") && !(this.turn == "White")) {
      return false;
    } else if (pieceName.contains("Black") && !(this.turn == "Black")) {
      return false;
    } else if (pieceName.contains("White") && this.turn == "White") {
      return true;
    } else if (pieceName.contains("Black") && this.turn == "Black") {
      return true;
    }
    return false;
  }

  //Allow a pipece to be dragged

  public void mouseDragged(MouseEvent me) {
    if (chessPiece == null)
      return;
    chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
  }

  //Logic after a piece has been dropped via a mouse release

  public void mouseReleased(MouseEvent e) {
    if(chessPiece == null)
      return;

    //Variables which will be used and debugging statements

    chessPiece.setVisible(false);
    Boolean success =false;
    Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
    String tmp = chessPiece.getIcon().toString();
    String pieceName = tmp.substring(0, (tmp.length()-4));
    Boolean validMove = false;
    int landingX = (e.getX()/75);
    int landingY  = (e.getY()/75);
    int xMovement = Math.abs((e.getX()/75)-startX);
    int yMovement = Math.abs((e.getY()/75)-startY);

    System.out.println("----------------------------------------------");
    System.out.println("The piece that is being moved is : "+pieceName);
    System.out.println("The starting coordinates are : "+"( "+startX+","+startY+")");
    System.out.println("The xMovement is : "+xMovement);
    System.out.println("The yMovement is : "+yMovement);
    System.out.println("The landing coordinates are : "+"( "+landingX+","+landingY+")");
    System.out.println("----------------------------------------------");

    //Logic for the king piece

    if (pieceName.contains("King")){
      if(isPlayersTurn(pieceName)){
        if ((xMovement == 0 && yMovement == 1 || xMovement == 1 && yMovement == 0 || xMovement == 1 && yMovement == 1)) {
          if(piecePresent(e.getX(), e.getY())){
            if(pieceName.contains("White")){
              if(checkWhiteOponent(e.getX(), e.getY())){
                if(!surroundingPiecesCheck(e)){
                  validMove = true;
                } else {
                  validMove = false;
                }
              } else {
                validMove = false;
              }
            } else {
              if(checkBlackOponent(e.getX(), e.getY())){
                if(!surroundingPiecesCheck(e)){
                  validMove = true;
                } else {
                  validMove = false;
                }
              } else {
                validMove = false;
              }
            }
          } else {
            if(!surroundingPiecesCheck(e)){
              validMove = true;
            } else {
              validMove = false;
            }
          }
        } else {
          validMove = false;
        }
      }
    }

    //Logic for the queen piece

    else if (pieceName.contains("Queen")){
      if(isPlayersTurn(pieceName)){
        Boolean inTheWay = false;
        if(((landingX < 0 ) || (landingX > 7)) || ((landingY < 0)||(landingY > 7))){
          validMove = false;
        } else if(((Math.abs(startX-landingX)!=0)&&(Math.abs(startY-landingY)==0))||((Math.abs(startX-landingX)==0)&&(Math.abs(landingY-startY)!=0))) {
          if(Math.abs(startX-landingX)!=0){
            int xDirection = Math.abs(startX-landingX);
            if(startX-landingX > 0){
              for(int i = 0; i < xDirection; i++){
                if(piecePresent(initialX-(i*75), e.getY())){
                  inTheWay = true;
                  break;
                } else{
                  inTheWay = false;
                }
              }
            } else{
              for(int i = 0; i < xMovement; i++){
                if(piecePresent(initialX+(i*75), e.getY())){
                  inTheWay = true;
                  break;
                } else{
                  inTheWay = false;
                }
              }
            }
          } else{
            int yDirection = Math.abs(startY-landingY);
            if(startY-landingY > 0){
              for(int i = 0; i< yDirection; i++){
                if(piecePresent(e.getX(), initialY-(i*75))){
                  inTheWay = true;
                  break;
                } else {
                  inTheWay = false;
                }
              }
            } else {
              for(int i = 0; i< yMovement; i++){
                if(piecePresent(e.getX(), initialY+(i*75))){
                  inTheWay = true;
                  break;
                } else {
                  inTheWay = false;
                }
              }
            }
          }
          if(inTheWay){
            validMove = false;
          }
          else {
            if(piecePresent(e.getX(), (e.getY()))){
              if(pieceName.contains("White")){
                if(checkWhiteOponent(e.getX(), e.getY())){
                  validMove = true;
                } else {
                  validMove = false;
                }
              } else {
                if(checkBlackOponent(e.getX(), e.getY())){
                  validMove = true;
                } else {
                  validMove=false;
                }
              }
            } else {
              validMove=true;
            }
          }
        } else if (Math.abs(startX-landingX) == Math.abs(startY-landingY)) {
          int distance = Math.abs(startX-landingX);
          validMove = true;

          if ((startX-landingX < 0)&&(startY-landingY<0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX+(i*75)), (initialY+(i*75)))) {
                inTheWay = true;
              }
            }
          } else if ((startX-landingX<0) && (startY-landingY>0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX+(i*75)), (initialY-(i*75)))) {
                inTheWay = true;
              }
            }
          } else if ((startX-landingX>0) && (startY-landingY>0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX-(i*75)), (initialY-(i*75)))) {
                inTheWay = true;
              }
            }
          } else if ((startX-landingX>0) && (startY-landingY<0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX-(i*75)), (initialY+(i*75)))) {
                inTheWay = true;
              }
            }
          }

          if (inTheWay) {
            validMove = false;
          } else {
            if (piecePresent(e.getX(), (e.getY()))) {
              if (pieceName.contains("White")) {
                if (checkWhiteOponent(e.getX(), e.getY())) {
                  validMove = true;
                } else {
                  validMove = false;
                }
              } else {
                if (checkBlackOponent(e.getX(), e.getY())) {
                  validMove = true;
                } else {
                  validMove = false;
                }
              }
            } else {
              validMove = true;
            }
          }
        } else {
          validMove = false;
        }
      }
    }

    //Logic for the rook piece

    else if(pieceName.contains("Rook")){
      if(isPlayersTurn(pieceName)){
        Boolean inTheWay = false;
        if(((landingX < 0 ) || (landingX > 7)) || ((landingY < 0)||(landingY > 7))){
          validMove = false;
        } else {
          if(((Math.abs(startX-landingX)!=0)&&(Math.abs(startY-landingY)==0))||((Math.abs(startX-landingX)==0)&&(Math.abs(landingY-startY)!=0))){
            if(Math.abs(startX-landingX)!=0){
              int xDirection = Math.abs(startX-landingX);
              if(startX-landingX > 0){
                for(int i = 0; i < xDirection; i++){
                  if(piecePresent(initialX-(i*75), e.getY())){
                    inTheWay = true;
                    break;
                  } else{
                    inTheWay = false;
                  }
                }
              } else{
                for(int i = 0; i < xMovement; i++){
                  if(piecePresent(initialX+(i*75), e.getY())){
                    inTheWay = true;
                    break;
                  } else{
                    inTheWay = false;
                  }
                }
              }
            } else{
              int yDirection = Math.abs(startY-landingY);
              if(startY-landingY > 0){
                for(int i = 0; i< yDirection; i++){
                  if(piecePresent(e.getX(), initialY-(i*75))){
                    inTheWay = true;
                    break;
                  } else {
                    inTheWay = false;
                  }
                }
              } else {
                for(int i = 0; i< yMovement; i++){
                  if(piecePresent(e.getX(), initialY+(i*75))){
                    inTheWay = true;
                    break;
                  } else {
                    inTheWay = false;
                  }
                }
              }
            }
            if(inTheWay){
              validMove = false;
            }
            else {
              if(piecePresent(e.getX(), (e.getY()))){
                if(pieceName.contains("White")){
                  if(checkWhiteOponent(e.getX(), e.getY())){
                    validMove = true;
                  } else {
                    validMove = false;
                  }
                } else {
                  if(checkBlackOponent(e.getX(), e.getY())){
                    validMove = true;
                  } else {
                    validMove=false;
                  }
                }
              } else {
                validMove=true;
              }
            }
        } else {
            validMove=false;
          }
        }
      }
    }

    //Logic for the bishop piece

    else if (pieceName.contains("Bishop")) {
      Boolean inTheWay = false;
      int distance = Math.abs(startX - landingX);
      if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
        validMove = false;
      } else {
        validMove = true;
        if (Math.abs(startX-landingX) == Math.abs(startY-landingY)) {
          if ((startX-landingX<0) && (startY-landingY<0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX+(i*75)), (initialY+(i*75)))) {
                inTheWay = true;
              }
            }
          } else if ((startX-landingX<0) && (startY-landingY>0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX+(i*75)), (initialY-(i*75)))) {
                inTheWay = true;
              }
            }
          } else if ((startX-landingX>0) && (startY-landingY>0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX-(i*75)), (initialY-(i*75)))) {
                inTheWay = true;
              }
            }
          } else if ((startX-landingX>0) && (startY-landingY< 0)) {
            for (int i = 0; i < distance; i++) {
              if (piecePresent((initialX-(i * 75)), (initialY+(i * 75)))) {
                inTheWay = true;
              }
            }
          }

          if (inTheWay) {
            validMove = false;
          } else {
            if (piecePresent(e.getX(), (e.getY()))) {
              if (pieceName.contains("White")) {
                if (checkWhiteOponent(e.getX(), e.getY())) {
                  validMove = true;
                } else {
                  validMove = false;
                }
              } else {
                if (checkBlackOponent(e.getX(), e.getY())) {
                  validMove = true;
                } else {
                  validMove = false;
                }
              }
            } else {
              validMove = true;
            }
          }
        } else {
          validMove = false;
        }
      }
    }

    //Logic for the knight piece

    else if(pieceName.contains("Knight")){
      if(isPlayersTurn(pieceName)){
        if(((landingX < 0)||(landingX>7))||((landingY<0)|| landingY > 7)){
          validMove = false;
        } else {
          if(((landingX == startX+1) && (landingY == startY+2)) || ((landingX==startX-1) &&(landingY ==
          startY+2))||((landingX==startX+2) && (landingY == startY+1))||((landingX==startX-2) &&(landingY==
          startY+1))||((landingX==startX+1) && (landingY == startY-2))||((landingX==startX-1) &&(landingY==
          startY-2))||((landingX==startX+2) && (landingY == startY-1))||((landingX==startX-2) &&(landingY==
          startY-1))){
            if(piecePresent(e.getX(), (e.getY()))){
              if(pieceName.contains("White")){
                if(checkWhiteOponent(e.getX(), e.getY())){
                  validMove=true;
                } else {
                  validMove=false;
                }
              } else {
                if(checkBlackOponent(e.getX(), e.getY())){
                  validMove=true;
                } else {
                  validMove=false;
                }
              }
            } else {
              validMove=true;
            }
          } else {
            validMove=false;
          }
        }
      }
    }

    //Logic for the black pawn piece

    else if(pieceName.equals("BlackPawn")){
      if(isPlayersTurn(pieceName)){
        if(startY==6){
          if(((yMovement==1)||(yMovement==2))&&(startY>landingY)&&(xMovement==0)){
            if(yMovement == 2){
              if((!piecePresent(e.getX(), e.getY()))&&(!piecePresent(e.getX(), (e.getY()+75)))){
                validMove = true;
              }
            }
            else {
              if(!piecePresent(e.getX(), e.getY())){
                validMove = true;
              }
            }
          }
          else if((yMovement==1)&&(startY>landingY)&&(xMovement==1)) {
            if(piecePresent(e.getX(), e.getY())){
              if(checkBlackOponent(e.getX(), e.getY())){
                validMove = true;
              }
            }
          }
        }
        else {
          if((yMovement==1)&&(startY>landingY)&&(xMovement==0)){
            if(!piecePresent(e.getX(), e.getY())){
              validMove = true; //TODO: Turn black pawn into queen
            }
          }
          else if((yMovement==1)&&(startY>landingY)&&(xMovement==1)) {
            if(piecePresent(e.getX(), e.getY())){
              if(checkBlackOponent(e.getX(), e.getY())){
                validMove = true;
              }
            }
          }
        }
      }
    }

    //Logic for the white pawn piece

    else if (pieceName.equals("WhitePawn")) {
      if (isPlayersTurn(pieceName)) {
        if (startY == 1) {
          if ((startX == (e.getX() / 75)) && ((e.getY() / 75) - startY) == 1 ||((e.getY() / 75) - startY) == 2) {
            if (((e.getY() / 75) - startY) == 2) {
              if (!piecePresent(e.getX(), e.getY()) && !piecePresent(e.getX(), (e.getY() - 75))) {
                validMove = true;
              } else {
                validMove = false;
              }
            } else {
              if ((!piecePresent(e.getX(), (e.getY())))) {
                validMove = true;
              } else {
                validMove = false;
              }
            }
          } else if (piecePresent(e.getX(), e.getY())) {
            if (checkWhiteOponent(e.getX(), e.getY())) {
              validMove = true;
              if (startY == 6) {
                success = true;
              }
            } else {
              validMove = false;
            }
          }
        } else {
          int newY = e.getY() / 75;
          int newX = e.getX() / 75;
          if ((startX - 1 >= 0) || (startX + 1 <= 7)) {
            if ((xMovement == yMovement) && (xMovement == 1) && (newY > startY)) {
              if (piecePresent(e.getX(), e.getY())) {
                if (checkWhiteOponent(e.getX(), e.getY())) {
                  validMove = true;
                  if (startY == 6) {
                    success = true;
                  }
                } else {
                  validMove = false;
                }
              } else {
                validMove = false;
              }
            } else {
              if (!piecePresent(e.getX(), (e.getY()))) {
                if ((startX == (e.getX() / 75)) && ((e.getY() / 75) - startY) == 1) {
                  if (startY == 6) {
                    success = true;
                  }
                  validMove = true;
                } else {
                  validMove = false;
                }
              } else {
                validMove = false;
              }
            }
          } else {
            validMove = false;
          }
        }
      }
    }

    //Hangle non-valid moves

    if(!validMove){
      int location=0;
      if(startY ==0){
      	location = startX;
      } else{
      	location = (startY*8)+startX;
      }
      String pieceLocation = pieceName+".png";
      pieces = new JLabel( new ImageIcon(pieceLocation) );
      panels = (JPanel)chessBoard.getComponent(location);
      panels.add(pieces);
    } else { //Handle valid moves
      winningMoveCheck(e.getX(), e.getY());
      if (this.turn == "White") {
        System.out.println("It's player 2 turn");
        this.turn = "Black";
      } else {
        System.out.println("It's player 1 turn");
        this.turn = "White";
      }

      //Convert piece colors if power piece taken

      if(success){
        if(pieceName.equals("WhitePawn")){
          int location = 56 + (e.getX()/75);
          if (c instanceof JLabel){
            Container parent = c.getParent();
            parent.remove(0);
            pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
            parent = (JPanel)chessBoard.getComponent(location);
            parent.add(pieces);
          }
          else{
            Container parent = (Container)c;
            pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
            parent = (JPanel)chessBoard.getComponent(location);
            parent.add(pieces);
          }
        } else {
          int location = 0 + (e.getX() / 75);
          if (c instanceof JLabel) {
            Container parent = c.getParent();
            parent.remove(0);
            pieces = new JLabel(new ImageIcon("BlackQueen.png"));
            parent = (JPanel) chessBoard.getComponent(location);
            parent.add(pieces);
          } else {
            Container parent = (Container) c;
            pieces = new JLabel(new ImageIcon("BlackQueen.png"));
            parent = (JPanel) chessBoard.getComponent(location);
            parent.add(pieces);
          }
        }
			}
      else {
        if(c instanceof JLabel){
          Container parent = c.getParent();
          parent.remove(0);
          parent.add( chessPiece );
        } else {
          Container parent = (Container)c;
          parent.add( chessPiece );
        }
        chessPiece.setVisible(true);
      }
    }
  }

  //Abstract methods

  public void mouseClicked(MouseEvent e){
  }

  public void mouseMoved(MouseEvent e){
  }

  public void mouseEntered(MouseEvent e){
  }

  public void mouseExited(MouseEvent e){
  }

  //Main method

  public static void main(String[] args){
    JFrame frame = new ChessProject();
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
    frame.pack();
    frame.setResizable(true);
    frame.setLocationRelativeTo( null );
    frame.setVisible(true);
  }
}
