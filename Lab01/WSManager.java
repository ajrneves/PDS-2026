import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class WSManager {
    private int dim;
    private List<String> linesInFile;

    public WSManager(int dim) {
        this.dim = dim;
        this.linesInFile = new ArrayList<>();
    }
    
    /* Validadores */
    public boolean validateSolverFileArgs(String[] args) {
        if(args.length == 1)
            return true;
        return false;
    }
    
    public boolean validateGeneratorFileArgs(String[] args) {
        if(args.length == 4)
            if((args[0].equals("-w") && args[2].equals("-s")) || (args[0].equals("-s") && args[2].equals("-w")))
                return true;
        if(args.length == 2 && args[0].equals("-w"))
            return true;
        return false;
    }

    /* Ex 1 - WSSolver */
    public Soup loadSoup(File file) {
        Soup soup = new Soup(this.dim);
        this.linesInFile = readFile(file);
        int linesRead = 0;
        for(String line : this.linesInFile){
            if(this.isLineEmpty(line))
                return null;

            if(this.isComment(line))
                continue;

            line = this.removeComments(line);
            if (linesRead < dim){//Ler linhas da sopa de letras
                if(this.validateSoupLine(line))
                    soup.addLine(line);
                else
                    return null;
                linesRead++;
            }
            else{//Ler palavras
                if(this.validateWordsLine(line)){
                    for(String word : line.split(";|,|\\s+")){
                        if(soup.containsWord(word)){
                            //9.	Todas as palavras da lista têm de estar no puzzle e apenas uma vez. 
                            System.err.println("[Erro]: Ficheiro mal formatado. A palavra " + word + " já existe na sopa de letras.");
                            return null;
                        }
                        String formattedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                        soup.addWord(formattedWord);
                    }
                }
                else
                    return null;
            }
        }
        return soup;
    }

    private List<String> readFile(File file) {
        List<String> linesInFile = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                linesInFile.add(line);
            }
        }
        catch(IOException e) {
            System.err.println("[Erro]: Ficheiro " + file.getName() + " não encontrado.");
            return null;
        }
        return linesInFile;
    }

    private boolean validateSoupLine(String line) {
        /*
            1.	O puzzle é sempre quadrado, com o tamanho máximo de 15x15.
            2.	As letras do puzzle estão em minúsculas.
            5.	As palavras são compostas por caracteres alfabéticos.
        */
        if (line.length() != dim) {
            //1.	O puzzle é sempre quadrado, com o tamanho máximo de 15x15.
            System.err.println("[Erro]: Ficheiro mal formatado. A dimensão da sopa de letras é diferente da especificada.");
            return false;
        }

        if(!line.equals(line.toLowerCase())){
            //2.	As letras do puzzle estão em minúsculas.
            System.err.println("[Erro]: Ficheiro mal formatado. Existem caracteres maiúsculos na sopa de letras.");
            return false;
        }

        if(!line.matches("[a-z]+")){
            //5.	As palavras são compostas por caracteres alfabéticos.
            System.err.println("[Erro]: Ficheiro mal formatado. Existem caracteres não alfabéticos na sopa de letras.");
            return false;
        }
        
        return true;
    }

    private boolean validateWordsLine(String line) {
        /*
            3.	Na lista, as palavras tem a primeira letra em maiúsculas.
            5.	As palavras são compostas por caracteres alfabéticos.
            7.	Cada linha pode ter mais do que uma palavra, separadas por vírgula, espaço ou ponto e vírgula.
            8.	As palavras têm de ter pelo menos 3 caracteres.
        */
        for (String word : line.split(";|,|\\s+")) {
            if (word.length() < 3) {
                //8.	As palavras têm de ter pelo menos 3 caracteres.
                System.err.println("[Erro]: Ficheiro mal formatado. Existem palavras com menos de 3 caracteres.");
                return false;
            }
            if (!word.matches("[A-Z][a-z]+")) {
                //3.	Na lista, as palavras tem a primeira letra em maiúsculas e as restantes em minúsculas.
                //5.	As palavras são compostas por caracteres alfabéticos.
                System.err.println("[Erro]: Ficheiro mal formatado. As palavras devem ter a primeira letra maiúscula e as restantes minúsculas.");
                return false;
            }
        }
        return true;
    }

    private boolean isComment(String line) {
        //3.	O caracter # sinaliza um comentário.
        return line.charAt(0) == '#'; 
    }

    private boolean isLineEmpty(String line) {
        //6.	No puzzle e na lista de palavras, o ficheiro não pode conter linhas vazias.
        if (line.length() == 0) {
            System.err.println("[Erro]: Ficheiro mal formatado. Existem linhas em branco na sopa de letras.");
            return true;
        }
        return false;
    }

    private String removeComments(String line) {
        //3.	O caracter # sinaliza um comentário.
        return line.split("#")[0].strip();
    }


    /* Ex 2 - WSGenerator */
    public Soup generateSoup(File file) {
        Soup soup = new Soup(this.dim);
        this.linesInFile = readFile(file);
        for(String line : this.linesInFile){
            for(String word : line.split(";|,|\\s+")){
                soup.addWord(word);
            }
        }
        soup.generateSoup();
        return soup;
    }

    public void saveSoup(String fileName, String soup){
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(soup);
        } catch (IOException e) {
            System.err.println("[Erro]: Ficheiro " + fileName + " não encontrado.");
        }   
    }

}
