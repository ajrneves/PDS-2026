import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Soup {
    private List<String> puzzle;
    private List<String> words;
    private int dim;

    public Soup(int dim) {
        this.dim = dim;
        this.puzzle = new ArrayList<>();
        this.words = new ArrayList<>();
    }

    public void addLine(String line){
        this.puzzle.add(line);
    }

    public void addWord(String word){
        word = word.toLowerCase();
        //10.	A lista de palavras pode conter palavras com partes iguais (por exemplo, pode conter FARO e FAROL). Nestes casos deve ser considerado apenas a maior (FAROL).
        for (String wordInList : this.words){
            if (word.contains(wordInList)){
                this.words.remove(wordInList);
                break;
            }
            if (wordInList.contains(word)){
                return;
            }
        }
        this.words.add(word);
    }

    public boolean containsWord(String word){
        return this.words.contains(word);
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for(String line : this.puzzle)
            result.append(line).append("\n");
        for(String word : this.words)
            result.append(word).append("\n");
        return result.toString();
    }

    public List<String> getWords(){
        return this.words;
    }

    public List<String> getPuzzle(){
        return this.puzzle;
    }

    public void generateSoup() {//Usei GPT neste método
        char[][] matrix = new char[this.dim][this.dim];
        Random random = new Random();

        // Initialize the matrix with spaces
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                matrix[i][j] = ' ';
            }
        }

        // Place each word in the matrix
        for (String word : this.words) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(this.dim);
                int col = random.nextInt(this.dim);
                int direction = random.nextInt(2); // 0 for horizontal, 1 for vertical

                if (direction == 0 && col + word.length() <= this.dim) { // Horizontal
                    boolean canPlace = true;
                    for (int k = 0; k < word.length(); k++) {
                        if (matrix[row][col + k] != ' ' && matrix[row][col + k] != word.charAt(k)) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        for (int k = 0; k < word.length(); k++) {
                            matrix[row][col + k] = word.charAt(k);
                        }
                        placed = true;
                    }
                } else if (direction == 1 && row + word.length() <= this.dim) { // Vertical
                    boolean canPlace = true;
                    for (int k = 0; k < word.length(); k++) {
                        if (matrix[row + k][col] != ' ' && matrix[row + k][col] != word.charAt(k)) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        for (int k = 0; k < word.length(); k++) {
                            matrix[row + k][col] = word.charAt(k);
                        }
                        placed = true;
                    }
                }
            }
        }

        // Fill empty spaces with random letters
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (matrix[i][j] == ' ') {
                    matrix[i][j] = (char) ('a' + random.nextInt(26));
                }
            }
        }

        // Convert matrix to puzzle format
        this.puzzle.clear();
        for (int i = 0; i < this.dim; i++) {
            this.puzzle.add(new String(matrix[i]));
        }
    }

    public String getSoup(){
        StringBuilder result = new StringBuilder();
        for(String line : this.puzzle)
            result.append(line).append("\n");

        for(String word : this.words){
            String formattedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            result.append(formattedWord).append("\n");
        }

        if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }
}