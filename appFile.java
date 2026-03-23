public class appFile extends storageFile{

///ENUM///
public enum AppType{ GAMING_OR_SOCIAL, PRODUCTIVITY, }

private int usage;
private AppType appType;

///______________________________CONSTRUCTORS____________________________________///

    public appFile (String name, Type fileType, int storage, boolean isBackup, int usage, AppType appType) {

        super (name, fileType, storage, isBackup);
        this.usage = usage;
        this.appType = appType;
    }

    public appFile (String name, Type fileType, int storage, int usage, AppType appType) {

        this (name, fileType, storage, false, usage, appType);
    }

///________________________________SETTERS & GETTERS____________________________________///

    public int getUsage() {return usage;}
    public AppType getAppType() {return appType;}

    public void setUsage(int usage) {this.usage = usage;}
    public void setAppType(AppType appType) {this.appType = appType;}

///__________________________________METHODS____________________________________________///


@Override
public int deletionScore(){

    int score = 0;

    int size = getStorage();
    boolean hasBackup = getIsBackup();

    if (size >= 1000 && hasBackup) {return 120;}

    if (size >= 5000) {score += 80;}
    else if (1000 <= size && size < 5000) {score += 70;}
    else if ( 500 <= size && size < 1000) {score += 50;}
    else if ( 100 <= size && size < 500) {score += 30;}
    else if ( 0 < size && size < 100) {score -= 10;}

    if (hasBackup) {score += 40;}

    if (usage == 0) {score += 30;}
    else if (usage > 0 && usage < 10) {score += 20;}
    else if (usage < 20 && usage >= 10) {score += 0;}
    else {score -= 10;}

    if (appType == AppType.GAMING_OR_SOCIAL) {score += 30;}
    else {score -= 20;}

    return score;
}

}
    
