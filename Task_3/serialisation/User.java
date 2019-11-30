package serialisation;

import java.io.*;
import java.util.Date;

public class User implements Serializable {
	 
    private static final long serialVersionUID = 7829136421241571165L;
     
    private String firstName;
    private String lastName;
    private int accountNumber;
    private Date dateOpened;
 
    public User(String firstName, String lastName, int accountNumber, Date dateOpened) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.dateOpened = dateOpened;
    }
       
    public String toString() {
        return "User[firstName="+firstName+",lastName="+lastName+",accountNumber="+accountNumber+",dateOpened="+dateOpened.toString()+"]";
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {      
        firstName = aInputStream.readUTF();
        lastName = aInputStream.readUTF();
        accountNumber = aInputStream.readInt();
        dateOpened = new Date(aInputStream.readLong());
    }
 
    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeUTF(firstName);
        aOutputStream.writeUTF(lastName);
        aOutputStream.writeInt(accountNumber);
        aOutputStream.writeLong(dateOpened.getTime());
    }
    
    public static void main(String[] args) throws IOException
    {
        User myDetails = new User("Wojciech", "Mostowski", 102825, new Date());
        System.out.println(myDetails);
 
        FileOutputStream fileOut = new FileOutputStream("user.dat");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(myDetails);
        out.close();
        fileOut.close();
 
        User deserializedUser = null;
        try {
        	FileInputStream fileIn = new FileInputStream("user.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserializedUser = (User)in.readObject();
            in.close();
            fileIn.close();
            System.out.println(deserializedUser);
        } catch (ClassNotFoundException cnfe) { cnfe.printStackTrace(); }
    }
}
