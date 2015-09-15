package properties;


/** Материал, из которого будут сделаны все вещи. Нужно для крафта, расчета веса предметов, и их свойств.
 * Например, двимеритовые доспехи блокируют магию. Или металлический посох проводит электричество, в отличие от деревянного
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
