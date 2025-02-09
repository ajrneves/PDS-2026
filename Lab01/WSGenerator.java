import java.io.File;

public class WSGenerator {
     
    public static void main(String[] args)
    {
        Soup soup;
        WSManager wsmanager = new WSManager(15);//1.	O puzzle é sempre quadrado, com o tamanho máximo de 15x15.

        if (wsmanager.validateGeneratorFileArgs(args)) {
            String wordlistFile = args[0].equals("-w") ? args[1] : args[3];
            File file = new File(wordlistFile);
            soup = wsmanager.generateSoup(file);
            String result = soup.getSoup();
            System.out.println(result);

            if (args.length == 4){
                String resultFile = args[0].equals("-s") ? args[1] : args[3];
                wsmanager.saveSoup(resultFile, result);
            }
            
        } else {
            System.err.println("[Erro]: Argumentos incorretos. <O número de argumentos não é correcto>");
        }
    }
 
}
