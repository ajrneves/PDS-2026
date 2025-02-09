import java.util.ArrayList;
import java.util.List;

public class Solver {
    private Soup soup;
    private List<WordLocation> foundWords;

    public Solver(Soup soup){
        this.soup = soup;
        this.foundWords = new ArrayList<WordLocation>();
    }
    
    public void solve(){
        for(String word : this.soup.getWords())
            this.searchWord(word);
    }

    private void searchWord(String word){
        for(int i = 0; i < this.soup.getPuzzle().size(); i++)
            for(int j = 0; j < this.soup.getPuzzle().get(i).length(); j++)
                if(this.firstLetterMatches(word, i, j))
                    if(this.tryAllDirections(word, i, j))
                        break;
    }
    
    private boolean firstLetterMatches(String word, int i, int j){
        return this.soup.getPuzzle().get(i).charAt(j) == word.charAt(0);
    }

    private boolean tryAllDirections(String word, int i, int j){
        for(Direction direction : Direction.values())
            if(this.wordInDirection(word, i, j, direction))
                if(this.findRemainingLetters(word, i, j, direction)){
                    this.foundWords.add(new WordLocation(word, i, j, direction));
                    return true;
                }
        return false;
    }

    private boolean findRemainingLetters(String word, int i, int j, Direction direction){
        for(int k = 1; k < word.length(); k++){
            int[] coords = direction.getCoords();
            int x = i + coords[0] * k;
            int y = j + coords[1] * k;
            if(x < 0 || x >= this.soup.getPuzzle().size() || y < 0 || y >= this.soup.getPuzzle().get(x).length())
                return false;
            if(this.soup.getPuzzle().get(x).charAt(y) != word.charAt(k))
                return false;
        }
        return true;
    }

    private boolean wordInDirection(String word, int i, int j, Direction direction){
        int[] coords = direction.getCoords();
        int x = i + coords[0];
        int y = j + coords[1];
        if(x < 0 || x >= this.soup.getPuzzle().size() || y < 0 || y >= this.soup.getPuzzle().get(x).length())
            return false;
        return this.soup.getPuzzle().get(x).charAt(y) == word.charAt(1);
    }

    public void printResults() {
        // Criar array 2D com '_'
        int dim = this.soup.getPuzzle().size();
        char[][] highlightedPuzzle = new char[dim][dim];
        for (int i = 0; i < dim; i++) 
            for (int j = 0; j < dim; j++) 
                highlightedPuzzle[i][j] = '_';

        // Highlight palavras encontradas
        for (WordLocation wordLocation : this.foundWords) {
            System.out.println(wordLocation);
            String word = wordLocation.getWord();
            int x = wordLocation.getX();
            int y = wordLocation.getY();
            int[] coords = wordLocation.getDirection().getCoords();

            for (int k = 0; k < word.length(); k++) {
                highlightedPuzzle[x + coords[0] * k][y + coords[1] * k] = Character.toUpperCase(word.charAt(k));
            }
        }
        
        // Print sopa com as palavras encontradas
        for (char[] line : highlightedPuzzle) {
            for (char c : line) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}
