import java.util.*;

//io
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

public class Manager {


private HashSet<storageFile> files;

    public Manager(){
        files = new HashSet<>();}

    public boolean addFile(storageFile file){
        return files.add(file);}

    public void clearFiles(){
        files.clear();}

    public boolean isEmpty(){
        return files.isEmpty();}

    public int size(){
        return files.size();}
    
    public ArrayList<storageFile> getSortedFiles() {

        ArrayList<storageFile> sorted = new ArrayList<>(files);    
        
        Collections.sort(sorted);

        return sorted;
    }

    public storageFile getTopDeletion(){

        if (files.isEmpty()) return null;

        PriorityQueue<storageFile> queue = new PriorityQueue<>(files);

        return queue.peek();
    }

    public void saveFiles(String filePath){

        try (FileWriter writer = new FileWriter(filePath)){


            java.io.File f = new java.io.File(filePath);

            System.out.println("Saving to: " + f.getAbsolutePath());
            System.out.println("Exists: " + f.exists());
            System.out.println(files.size() + " files saved");

            for (storageFile file : files){
        
                if (file instanceof appFile){

                appFile app = (appFile) file;

                writer.write(String.format(
                "%s,%s,%d,%b,%d,%s\n",
                app.getName(),
                app.getFileType(),
                app.getStorage(),
                app.getIsBackup(),
                app.getUsage(),
                app.getAppType()
                ));
                }
                else if (file instanceof mediaFile){

                mediaFile media = (mediaFile) file;

                writer.write(String.format(
                "%s,%s,%d,%b,%s,%d,%d\n",
                media.getName(),
                media.getFileType(),
                media.getStorage(),
                media.getIsBackup(),
                media.getMediaType(),
                media.getOpens(),
                media.getMediaLength()
                ));
                }
            }
            System.out.println("Files successfully saved!");
        }

        catch (IOException e){
            System.out.println("Error writing file!");
        }

    }

    public void loadFiles(String filePath){

        files.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            
        String line;

            while ((line = reader.readLine()) != null){

                String[] parts = line.split(",");

                if (parts.length < 4) continue;

                String name = parts[0];
                storageFile.Type fileType = storageFile.Type.valueOf(parts[1]);
                int storage = Integer.parseInt(parts[2]);
                boolean backup = Boolean.parseBoolean(parts[3]);

                if (fileType == storageFile.Type.app && parts.length < 6) continue;
                if (fileType == storageFile.Type.media && parts.length < 7) continue;

                if (fileType == storageFile.Type.app){ 
                    int usage = Integer.parseInt(parts[4]);
                    appFile.AppType app = appFile.AppType.valueOf(parts[5]);
                    
                    storageFile f = new appFile(name, fileType, storage, backup, usage, app);
                    files.add(f);
                }
                else{
                    mediaFile.MediaType media = mediaFile.MediaType.valueOf(parts[4]);
                    int opens = Integer.parseInt(parts[5]);
                    int mediaLength = Integer.parseInt(parts[6]);
                    if (media == mediaFile.MediaType.png){

                        storageFile f = new mediaFile(name, fileType, storage, backup, media, opens, 0);
                        files.add(f);}
                    else {

                        storageFile f = new mediaFile(name, fileType, storage, backup, media, opens, mediaLength);
                        files.add(f);}
                }   
            }
            System.out.println("Files loaded!");
        }

        catch(FileNotFoundException e){
            System.out.println("No save file found. Making new");
        }
        catch(IOException e){
            System.out.println("Error reading file.");
        }
        }
}
