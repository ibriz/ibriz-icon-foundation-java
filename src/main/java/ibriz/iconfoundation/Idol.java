package ibriz.iconfoundation;

import java.math.BigInteger;

public class Idol {
    String owner;

    String name;
    BigInteger age;
    String gender;
    String ipfs_handle;

    public Idol(String name, BigInteger age, String gender, String ipfs_handle) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.ipfs_handle = ipfs_handle;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getAge() {
        return age;
    }

    public void setAge(BigInteger age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIpfs_handle() {
        return ipfs_handle;
    }

    public void setIpfs_handle(String ipfs_handle) {
        this.ipfs_handle = ipfs_handle;
    }
}
