public class mediaFile extends storageFile implements Score{

///ENUM///
public enum MediaType{ mp4, png }

private MediaType mediaType; 
private int opens;
private int mediaLength;

///______________________________CONSTRUCTORS____________________________________///

    public mediaFile (String name, Type fileType, int storage, boolean isBackup,
        MediaType mediaType, int opens, int mediaLength) {
            
            super (name, fileType, storage, isBackup);
            this.mediaType = mediaType;
            this.opens = opens;
            this.mediaLength = mediaLength;
        }

    public mediaFile (String name, Type fileType, int storage, boolean isBackup,
        MediaType mediaType, int mediaLength) {

            this (name, fileType, storage, isBackup, mediaType, 0, mediaLength);
        }

///______________________________SETTERS & GETTERS__________________________________///
    
    public MediaType getMediaType() {return mediaType;}
    public int getOpens() {return opens;}
    public int getMediaLength() {return mediaLength;}

    public void setMediaType(MediaType mediaType) {this.mediaType = mediaType;} 
    public void setOpens(int opens) {this.opens = opens;}
    public void setMediaLength(int mediaLength) {this.mediaLength = mediaLength;}

///__________________________________METHODS____________________________________________///

@Override
public int deletionScore() {

    int score = 0;

    int size = getStorage();
    boolean hasBackup = getIsBackup();

    if (hasBackup) return 100;

    if (mediaType == MediaType.png) {

        if (size >= 30) {score += 60;}
        else if (30 > size && size >= 10) {score += 40;}
        else {score -= 20;}

        if (opens > 5) {score -= 20;}
        else if (opens == 0) {score += 40;}
        else {score += 10;}
    }
    else {

        if (size >= 1000) {score += 60;}
        else if (300 <= size && size < 1000) {score += 40;} 
        else if (100 <= size && size < 300) {score += 20;}
        else {score += 0;}

        if (opens > 5) {score -= 20;}
        else if (opens == 0) {score += 30;}
        else {score += 20;}

        if (mediaLength >= 300) {score += 30;}
        else if (60 <= mediaLength && mediaLength < 300) {score += 10;}
        else {score -= 10;}
    }

    return score;
}

}
