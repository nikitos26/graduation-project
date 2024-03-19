package entities;

import lombok.Data;

@Data
public class CreateProductToCart {
    String position_id;
    int product_id;
    String product_key;
    int quantity;
    int shop_id;
}
