import java.io.File;

public class WSSolver {   
    public static void main(String[] args)
    {
        Soup soup;
        WSManager wsmanager = new WSManager(15);//1.	O puzzle é sempre quadrado, com o tamanho máximo de 100x100.

        if(wsmanager.validateSolverFileArgs(args))
        {
            File file = new File(args[0]);
            soup = wsmanager.loadSoup(file);
            Solver solver = new Solver(soup);
            solver.solve();
            solver.printResults();
        }
        else
            System.err.println("[Erro]: Argumentos incorretos. <O número de argumentos não é correcto>");
    }
 
}
