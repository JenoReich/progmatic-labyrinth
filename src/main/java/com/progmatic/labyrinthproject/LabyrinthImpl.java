package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private int width;
    private int height;
    public Coordinate[][] labyrinth = new Coordinate[width][height];
    private Coordinate playerPosition;

    public LabyrinthImpl() {
        this.labyrinth=new Coordinate[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                labyrinth[j][i]=new Coordinate(j, i);
            }
        }
        
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            labyrinth = new Coordinate[width][height];

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth[ww][hh] = new Coordinate(ww, hh);
                             {
                                try {
                                    setCellType(labyrinth[ww][hh], CellType.WALL);
                                } catch (CellException ex) {
                                    Logger.getLogger(LabyrinthImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            break;
                        case 'E':
                            labyrinth[ww][hh] = new Coordinate(ww, hh);
                             {
                                try {
                                    setCellType(labyrinth[ww][hh], CellType.END);
                                } catch (CellException ex) {
                                    Logger.getLogger(LabyrinthImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            break;
                        case 'S':
                            labyrinth[ww][hh] = new Coordinate(ww, hh);
                             {
                                try {
                                    setCellType(labyrinth[ww][hh], CellType.START);
                                } catch (CellException ex) {
                                    Logger.getLogger(LabyrinthImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            break;

                        case ' ':
                            labyrinth[ww][hh] = new Coordinate(ww, hh);
                             {
                                try {
                                    setCellType(labyrinth[ww][hh], CellType.EMPTY);
                                } catch (CellException ex) {
                                    Logger.getLogger(LabyrinthImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public int getWidth() {
        if (labyrinth == null) {
            return -1;
        } else {
            return this.width;
        }

    }

    @Override
    public int getHeight() {
        if (labyrinth == null) {
            return -1;
        } else {
            return this.height;
        }

    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {

        
        if (c.getCol() >= this.width || c.getCol() < 0 || c.getRow() >= this.height || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "invalid coordinate");
        } else {
           int col=c.getCol();
           int row=c.getRow();
           
           return this.labyrinth[col][row].getCellsType();
             
        }
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        for (Coordinate[] coordinates : labyrinth) {
            for (Coordinate coordinate : coordinates) {
                try {
                    setCellType(coordinate, CellType.EMPTY);
                } catch (CellException ex) {
                    Logger.getLogger(LabyrinthImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getCol() >= this.width || c.getCol() < 0 || c.getRow() >= this.height || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "invalid coordinate");
        } else {
            for (Coordinate[] coordinates : labyrinth) {
                for (Coordinate coordinate : coordinates) {
                    if (coordinate.getCol() == c.getCol() && coordinate.getRow() == c.getRow()) {
                        coordinate.setCellsType(type);
                    }
                }

            }

        }
        
        if (type.equals(CellType.START)) {
            setPlayerPosition(c);
        }
    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }
    
    public void setPlayerPosition(Coordinate c){
        this.playerPosition=c;
    }
    
    
    
    
    

    @Override
    public boolean hasPlayerFinished() {
        if (this.getPlayerPosition().getCellsType().equals(CellType.END)) {
            return true;
        } else{
            return false;
        }
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> directions=new ArrayList<>();
        int col=getPlayerPosition().getCol();
        int row=getPlayerPosition().getRow();
        
        if (this.labyrinth[col+1][row].getCellsType().equals(CellType.EMPTY)) {
            directions.add(Direction.EAST);
        }
        
        if (this.labyrinth[col-1][row].getCellsType().equals(CellType.EMPTY)) {
            directions.add(Direction.WEST);
        }
        
        if (this.labyrinth[col][row-1].getCellsType().equals(CellType.EMPTY)) {
            directions.add(Direction.NORTH);
        }
        
        if (this.labyrinth[col][row+1].getCellsType().equals(CellType.EMPTY)) {
            directions.add(Direction.SOUTH);
        }
        
        return directions;
        
        
        
        
        
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        if (this.possibleMoves().contains(direction)) {
            int col=getPlayerPosition().getCol();
            int row=getPlayerPosition().getRow();
            
            switch(direction){
                case EAST:setPlayerPosition(new Coordinate(col+1, row));
                case WEST:setPlayerPosition(new Coordinate(col-1, row));
                case NORTH:setPlayerPosition(new Coordinate(col, row-1));
                case SOUTH:setPlayerPosition(new Coordinate(col, row+1));
            }
        } else{
            throw new InvalidMoveException();
        }
    }

}
