package symbol;

/**
* ArraySymbol class
* Stores all inherited symbols + additional size parameter
*/
public class ArraySymbol extends Symbol {
    public int array_size;

    public ArraySymbol(String name, int type, int offset, int size) {
        this.name = name;
        this.type = type;
        this.offset = offset;
        this.array_size = size;
    }
    public ArraySymbol(int type, String name, int size) {
        this.name = name;
        this.type = type;
        this.array_size = size;
    }
}
