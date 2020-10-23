import java.util.ArrayList;
import java.io.*;

public class IndexManager {
    
    public void addIndex(Index index) throws IOException, ClassNotFoundException {
        String filename = "src/data/Index.dat";
        ArrayList<Index> indexList = IOController.readFile(filename);
        indexList.add(index);
        IOController.writeFile(filename, indexList);
    }

    public void removeIndex(int indexNum) throws IOException, ClassNotFoundException {
        String filename = "src/data/Index.dat";
        Index toRemove = null;
        ArrayList<Index> indexList = IOController.readFile(filename);
        for (Index i: indexList) {
            if (i.getIndexNum() == indexNum) {
                toRemove = i;
                break;
            }
        }
        if (toRemove != null) indexList.remove(toRemove);
        IOController.writeFile(filename, indexList);
    }

}
