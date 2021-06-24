package sample;

public class CartTable {

    static int idCart;
    String ProdName;
    String ProdBy;
    int Price;

    public CartTable(int idCart, String prodName, String prodBy, int price) {
        this.idCart = idCart;
        ProdName = prodName;
        ProdBy = prodBy;
        Price = price;
    }

    public static int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public String getProdBy() {
        return ProdBy;
    }

    public void setProdBy(String prodBy) {
        ProdBy = prodBy;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
