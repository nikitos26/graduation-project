package entities;

import lombok.Data;

@Data
public class Position {
    public String position_id;
    public int product_id;
    public String product_key;
    public int quantity;
    public int shop_id;
}
