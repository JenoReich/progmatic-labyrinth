/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

/**
 *
 * @author Reich Jenő
 */
public class RandomPlayerImpl implements Player{
   private LabyrinthImpl l;
   

    @Override
    public Direction nextMove(Labyrinth l) {
        
        Direction d=Direction.EAST;
        int random=(int)(Math.random()*4);
        String[]directions={"NORTH","EAST","SOUTH","WEST"};
        String randomDirection=directions[random];
        if (randomDirection.equals("NORTH") && l.possibleMoves().contains(Direction.NORTH)) {
            return Direction.NORTH;
        } else if(randomDirection.equals("EAST") && l.possibleMoves().contains(Direction.EAST)){
            return Direction.EAST;
                
        } else if(randomDirection.equals("SOUTH") && l.possibleMoves().contains(Direction.SOUTH)){
            return Direction.SOUTH;
        } else if (l.possibleMoves().contains(Direction.WEST)) {
            return Direction.WEST;
        }
        
        return d;
        
    }
    
}
