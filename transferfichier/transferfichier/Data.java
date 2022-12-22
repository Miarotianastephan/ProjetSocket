package data;

import java.io.Serializable;

public class Data  implements Serializable {
    String nameData;
    String extension;
    byte[] data;


    public Data() {
    }
    public Data(String nameData, String extension, byte[] data) {
        this.nameData = nameData;
        this.extension = extension;
        this.data = data;
    }
    public String getNameData() {
        return nameData;
    }
    public void setNameData(String nameData) {
        this.nameData = nameData;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    };
}
