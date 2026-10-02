public interface IPizza2 extends IMenuItems {
    void setSize(String size);
    void setCrust(String crust);
    void setSauce(String sauce);
    void setCheese(String cheese);
    void addToppings(String topping);
}