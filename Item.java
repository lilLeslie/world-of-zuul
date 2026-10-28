public class Item {
    private String name;
    private String description;
    private int weight;

    public Item(String name, String description, int weight){
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public String toString(){
        return name + description + "Weight: " + weight;
    }

}
