import java.util.Objects;

public abstract class storageFile implements Comparable<storageFile>, Score{

public enum Type {app, media};

private Type fileType;
private String name;
private int storage;
private boolean isBackup;

public abstract int deletionScore();

///______________________________CONSTRUCTORS____________________________________///

public storageFile(String name, Type fileType, int storage, boolean isBackup){
    this.name = name;
    this.fileType = fileType;
    this.storage = storage;
    this.isBackup = isBackup;
}

///Overload
public storageFile(String name, Type fileType, int storage) {

    //Calling previous constructor
    this (name, fileType, storage, false);
}

///____________________________SETTERS & GETTERS_________________________________///

public Type getFileType() {return fileType;}
public String getName() {return name;}
public int getStorage() {return storage;}
public boolean getIsBackup() {return isBackup;}

public void setFileType(Type fileType) {this.fileType = fileType;}
public void setName(String name) {this.name = name;}
public void setStorage(int storage) {this.storage = storage;}
public void setIsBackup(boolean isBackup) {this.isBackup = isBackup;}

///_________________________________METHODS____________________________________///


@Override
public String toString(){

    //Differentiates Larger file sizes >= 1000MB as GB files instead of MB
    String size;

    //Uses String.format to add everything neatly
    if (storage >= 1000){ //Uses double division to show decimal places in the GB storages
        size = String.format("%.2f GB", getStorage()/1000.0); 
    }
    else { //If <1000Mb then it stays normal and in int format
        size = String.format("%d MB", getStorage());
    }

    String media = "";

    if (this instanceof mediaFile){
        mediaFile m = (mediaFile) this;

        if (m.getMediaType() == mediaFile.MediaType.png){
            media += ".png";}
        else { media += ".mp4";}
    }

    //Indicates whether or not it is a backup, also adds the amount of storage something takes up
    return getName() + media + " (" + size + ")" + (getIsBackup() ? " [Backup]" : "");   
}

@Override
public boolean equals(Object obj){

    //Detects for equal files (will be using this later on)
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    storageFile other = (storageFile) obj;
    
    //Two objects are equals if they share the same name!
    return name != null && name.equalsIgnoreCase(other.name) && fileType == other.fileType;
}

@Override
public int hashCode(){
    return Objects.hash(name.toLowerCase(), fileType);
}

@Override
public int compareTo(storageFile other) {

    int thisScore = this.deletionScore();
    int otherScore = other.deletionScore();

    // Descending order (highest score first)
    return Integer.compare(otherScore, thisScore);
}

}
