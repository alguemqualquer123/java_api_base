package types;

// http://localhost:8000/app
// {
// "name": "Vinicius",
// "age": 21
// }


public class Types { 
    private String name;
    private int age;

    public Types(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
