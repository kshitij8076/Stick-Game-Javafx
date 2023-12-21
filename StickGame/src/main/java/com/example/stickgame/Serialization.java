package com.example.stickgame;

import java.io.*;

public class Serialization {

    public void serialize(User user) throws IOException {
        FileOutputStream file =null;
        ObjectOutputStream out = null;
        try {
            file = new FileOutputStream("/saved/user.txt");
            out = new ObjectOutputStream(file);
            out.writeObject(user);
        }
        catch (Exception e){
            System.out.println("Error in Serializing ");
        }
        finally {
            if(file != null){
                file.close();
            }
            if(out != null){
                out.close();
            }
        }
    }

    public Boolean check(){
        return true;
    }
}
