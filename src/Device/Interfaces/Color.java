package Device.Interfaces;

public enum Color {
    RED("RED"),
    BLUE("BLUE"),
    GREEN("GREEN"),
    WHITE("WHITE");

    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor(){
        return color;
    }
}
