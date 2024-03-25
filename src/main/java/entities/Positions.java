package entities;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Positions {
    public ArrayList<Position> positions = new ArrayList<>();

    public Positions(Position position){
        this.positions.add(position);
    }
}
