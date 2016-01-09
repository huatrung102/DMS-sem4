/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility;

/**
 *
 * @author TrungHTH
 */


import java.io.*;
import java.util.Arrays;

public class UploadedFile {

    private String contentType;
    private String name;
    private File file;

    public UploadedFile(String name, String contentType, File file) {
        this.contentType = contentType;
        this.name = name;
        this.file = file;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return name;
    }

    public void setFileName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public byte[] getContents() throws IOException {
        if (file == null) {
            return null;
        }
        byte r[] = new byte[(int) file.length()]; // we don't support large files!!!
        FileInputStream is = new FileInputStream(file);
        byte rr[] = Arrays.copyOf(r, is.read(r));
        is.close();
        return rr;
    }

    public InputStream getInputstream() throws FileNotFoundException {
        if (file == null) {
            return null;
        }
        return new FileInputStream(file);
    }

    public int getSize() {
        if (file == null) {
            return 0;
        }
        return (int) file.length();
    }

    @Override
    public String toString() {
        return "UploadedFile{" + "contentType=" + contentType + ",\n name=" + name + ",\n file=" + file + "\n}";
    }
}

