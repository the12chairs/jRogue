package properties;


/**
 * Material wich describes what items are made of. For craft, stats, etc
 */

public class Material {

    String name;

    public Material(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}