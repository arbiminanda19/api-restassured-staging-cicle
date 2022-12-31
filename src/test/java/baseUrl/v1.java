package baseUrl;

import helper.accessFile;

public class v1 {

    accessFile accessFile = new accessFile();
    String file_baseUrl = "src/test/resources/data/baseUrl.txt";
    String stagingCicle = accessFile.readFromFile(file_baseUrl);

    public String getStagingCicle() {
        return stagingCicle;
    }

}
