package sample;
// Encapsulation
public class Clothes {
    // created attributes of the class.
    private String name;
    private String ImageSrc;
    private String ImageSrc2;

    public String getImageSrc2() {
        return ImageSrc2;
    }

    public void setImageSrc2(String imageSrc2) {
        ImageSrc2 = imageSrc2;
    }

    private String Brand;
    private int Price;

    //created getter and setter methods which help me write and read data from this class.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return ImageSrc;
    }

    public void setImageSrc(String image) {
        ImageSrc = image;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getPrice() {
        return  Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
